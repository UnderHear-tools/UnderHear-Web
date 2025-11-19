# Repository Guidelines

## Project Structure & Module Organization
- `UnderHear-frontend/` hosts the Vue 3 app: `src/components` for reusable UI, `src/views` for routed pages, `src/api` for HTTP clients, and `src/assets` for static media; shared layout lives in `src/layout`.
- `UnderHear-backend/` is a Spring Boot service: application code sits in `src/main/java/com/underhear` (organized into `config`, `controller`, `dto`, `entity`, `mapper`, `service`, `util`), resources such as `application.properties` live under `src/main/resources`, and tests belong in `src/test/java`.
- Keep environment samples in `UnderHear-frontend/.env.example` and secret-free backend defaults in `UnderHear-backend/src/main/resources/application.properties`.

## Build, Test, and Development Commands
- Frontend: `npm install` (first run), `npm run dev` (Vite dev server on 5173 with hot reload), `npm run build` (type-check via `vue-tsc -b` then emit production bundle), `npm run preview` (serve built assets locally).
- Backend: `mvn spring-boot:run` (bootstraps the API with the configured MySQL and JWT secrets), `mvn clean package` (produces the runnable jar), `mvn test` (executes the JUnit/Spring Boot test suite).

## Coding Style & Naming Conventions
- Vue/TypeScript uses 2-space indentation, `<script setup lang="ts">`, PascalCase component files (e.g., `MainHeader.vue`), and `camelCase` composables/stores; keep Tailwind utility classes grouped by layout → spacing → color.
- Java code follows standard 4-space indentation, package-by-layer structure, and `UpperCamelCase` classes with `lowerCamelCase` fields/methods. Place configuration properties in dedicated `@ConfigurationProperties` classes (e.g., `GithubOAuthProperties`).
- Run formatters before committing: use your IDE’s TypeScript/Vue formatter for the frontend and `mvn fmt:format` (if configured) or IntelliJ’s default for Java. Keep imports ordered (java.*, org.*, com.*).

## Testing Guidelines
- Backend tests rely on Spring Boot Test + JUnit 5 (`src/test/java/com/underhear`). Name classes `<UnitUnderTest>Tests` and favor `@SpringBootTest` only when context loading is required; otherwise prefer slice tests.
- Frontend currently lacks automated tests—add Vitest or Cypress alongside `src/tests` and document the command (e.g., `npm run test:unit`) when introduced. Maintain >80% coverage for shared utilities and security-critical flows.

## Commit & Pull Request Guidelines
- Follow the enforced format `<type>(<scope>): <subject>` with `type ∈ {feat, fix, docs, style, refactor, perf, test, chore, revert, merge, sync}`. Example: `feat(controller): add github oauth login`.
- Subjects should be ≤50 characters, written in Chinese when it improves clarity, and omit trailing punctuation. Use `scope=*` only when the change spans multiple layers.
- PRs must include: concise summary, linked issue or requirement ID, screenshots/GIFs for UI-facing changes, and backend API contracts or curl examples when endpoints change. Ensure CI lint/build/test steps are green before requesting review.

## Security & Configuration Tips
- Never commit real credentials. Copy `.env.example` and inject secrets via environment variables or local `.env`. For backend secrets, prefer overriding `application.properties` using `SECURITY_JWT_*` and `GITHUB_OAUTH_*`.
- MySQL schemas live off-repo; document schema migrations in the PR description and share SQL scripts under `docs/db/` when applicable.
