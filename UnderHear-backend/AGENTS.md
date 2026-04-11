# AGENTS.md

本文件为 `UnderHear-Web/UnderHear-backend` 模块的约束与规范文档，补充工作区级规则。后续对该后端项目的修改，默认遵循本文件。

## 项目定位

- 技术栈以仓库当前内容为准：Spring Boot 4.0.2、Java 25、Maven、MyBatis、MySQL、Redis、JWT、JustAuth、Lombok。
- 当前后端是典型的分层结构：`controller`、`service`、`mapper`、`converter`、`pojo`、`security`、`config`、`util`。

## 分层职责

- `controller` 只负责接口路由、参数绑定、鉴权入口、调用 service、返回 `ApiResponse`。
- `service` 负责业务编排、事务、调用 mapper、调用外部服务、失败补偿。
- `mapper` 负责数据库访问，当前项目统一使用 MyBatis 注解 SQL，不使用 XML mapper。
- `converter` 只负责对象转换，保持纯静态工具类，不放数据库、网络、缓存等副作用逻辑。
- `pojo.entity` 放实体对象，`pojo.dto.request` / `pojo.dto.response` 放接口 DTO。
- `security` 放 JWT、Cookie、会话白名单等认证相关能力；新增鉴权逻辑优先复用这一层。

## DTO、Entity 与转换规则

- Dort 表示 `Data Object Request`。若一个数据对象不是由 Entity、其转换对象，或这些转换对象继续转换而来，则归类为 Dort。
- Dore 表示 `Data Object Response`。凡由 Entity、其转换对象，或这些转换对象继续转换得到的数据对象，均归类为 Dore。
- 新增请求 DTO 放在 `pojo.dto.request`，命名以 `Dort` 结尾。
- 新增响应 DTO 放在 `pojo.dto.response`，命名以 `Dore` 结尾。
- 实体对象放在 `pojo.entity`，保持与数据库字段语义一致，命名使用单数形式。
- DTO 与 Entity 之间的转换统一放到 `ToEntity`、`ToDort`、`ToDore` 中；不要把转换逻辑散落在 controller 或 mapper 里。
- 当前简单数据对象普遍使用 Lombok `@Data`；新增同类对象时沿用这一模式。

## Controller 约定

- 使用 `@RestController` + 类级 `@RequestMapping` 组织路由。
- 成功响应统一返回 `ApiResponse<T>`，优先使用 `ApiResponse.success(...)`。
- 不要在 controller 中直接拼失败响应；业务失败统一抛 `BizException`，交给全局异常处理器。
- 需要登录态的接口，沿用 `@CookieValue(value = "auth_token", required = false)` 读取 token，再通过 `SessionAuthService` 获取当前用户。
- multipart/form-data 接口沿用 `@PostMapping(..., consumes = MediaType.MULTIPART_FORM_DATA_VALUE)`、`@ModelAttribute`、`@Valid` 这一套绑定方式。
- controller 不直接操作数据库，不直接写 JWT，不直接手拼 `Set-Cookie`。

## Service 约定

- service 接口与实现分开放置，实现在对应业务子包的 `impl` 下。
- service 负责业务主流程、跨表操作、外部系统调用与必要的失败补偿。
- 多步数据库写入或“写库 + 写外部系统”的流程，按现有模式在 service 层组织；需要事务时显式加 `@Transactional`。
- 对外部副作用先执行、数据库后落库的流程，必须考虑回滚或清理。例如已发布站点但数据库写入失败时，要执行清理逻辑。
- 修改现有类时，沿用该类附近既有注入风格：
  - 业务 service 当前多使用字段注入 `@Autowired`
  - 安全/基础设施类当前多使用构造器注入
- 不要为了“统一风格”顺手全量改注入方式。

## Mapper 与 SQL 约定

- mapper 使用 `@Mapper` 接口 + 注解 SQL；没有充分理由时，不引入 XML mapper。
- 多行 SQL 使用 Java 文本块 `"""`，并保持与现有 mapper 一致的排版。
- 有多个简单参数时使用 `@Param` 明确命名。
- 数据库字段保持下划线命名，Java 字段保持驼峰命名，依赖现有的 `map-underscore-to-camel-case=true`。
- 涉及 `user`、`application` 等表时，沿用现有 SQL 中的反引号写法，不要混用多套风格。
- 写操作返回受影响行数时，由 service 判断结果是否符合预期；不要在 mapper 层吞掉失败。

## 参数校验与异常处理

- 请求参数校验放在 Dort 上，优先使用 `jakarta.validation` 注解。
- 文件上传这类无法直接用 `@NotBlank` 表达的约束，沿用当前 `@AssertTrue` 自定义校验方法的写法。
- 新增业务错误时，先补充 `ErrorCode`，再在业务中抛出 `BizException(ErrorCode.XXX)`。
- 非必要不要直接 new 通用 `RuntimeException` 表达业务失败。
- 统一依赖 `GlobalExceptionHandler` 输出错误响应；不要在各个 controller 中重复写 try/catch。
- 当前 `NOT_LOGIN` 的返回策略是业务码表示未登录，但 HTTP 状态仍为 `200 OK`。这是现有前后端契约，除非任务明确要求，否则不要擅自改动。

## 认证与安全约定

- JWT 的生成与解析统一通过 `JwtTokenService`。
- token 的白名单、失效、当前用户解析统一通过 `SessionAuthService`。
- Cookie 的写入与清理由 `AuthCookieService` 统一处理；不要在 controller 或 filter 中手写 Cookie 头。
- 新增登录、登出、会话失效相关逻辑时，先复用现有 `JwtTokenService`、`SessionAuthService`、`AuthCookieService`，不要旁路实现第二套 token 机制。
- OAuth 回调控制器负责第三方授权交互和写 cookie；用户注册/登录/更新逻辑放在对应 service 中。

## 配置与外部服务

- 配置项默认写在 `src/main/resources/application.properties`，读取方式沿用 `@Value` 或现有配置类注入方式。
- 涉及数据库、Redis、OAuth、JWT、Light OSS 的配置键名时，优先复用已有命名，不要发明新的同义配置。
- 除非任务明确要求，不要顺手改动现有敏感配置值，也不要在提交说明里重复抄出密钥内容。
- 新增外部 HTTP 调用时，优先参考 `LightOssPublishServiceImpl` 的模式：通过 `RestClient` 调用、集中翻译外部错误、对外抛业务异常。

## 注释与代码风格

- 每段功能都必须添加注释，以便维护。
- 保持当前风格：类名、方法名、字段名使用英文；代码注释以中文为主，必要时可混合英文术语。
- 注释只解释业务意图、约束或不直观的细节；不要写空洞注释。
- 不要为了“更高级”引入与现有代码不一致的大型抽象、通用基类、响应包装器或对象映射框架。
- 新增代码优先复用现有工具类与转换器，例如 `ShortUuidGenerator`、`ApplicationUuidGenerator`、`FileSizeFormatter`。

## 修改边界

- 不要把当前 MyBatis 注解 SQL 改造成 JPA、MyBatis XML 或 Repository 体系，除非任务明确要求。
- 不要把现有 `Dort/Dore/Entity` 命名体系改名成其他 DTO/VO/BO 体系。
- 不要擅自修改统一响应结构 `{ code, message, data }`。
- 不要在无明确需求时修改登录态载体、Cookie 名、JWT 结构、Redis key 规则、应用 URL 拼接规则等既有契约。
