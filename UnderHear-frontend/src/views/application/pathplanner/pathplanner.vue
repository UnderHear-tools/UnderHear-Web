<template>
  <div class="pathplanner-container">
    <div class="pathplanner-header">
      <h1 class="pathplanner-title">路径规划器</h1>
      <p class="pathplanner-subtitle">矩阵地图 + 多算法可视化（最短路径｜全覆盖）</p>
    </div>

    <div class="layout">
      <!-- 左侧：控制面板（吸顶） -->
      <aside class="sidebar">
        <div class="controls pathplanner-card">
          <div class="row">
            <div class="group-l">
              <label>地图尺寸</label>
              <div class="inline">
                <input type="number" min="1" max="60" v-model.number="rows" :disabled="isRunning" />
                <span>×</span>
                <input type="number" min="1" max="80" v-model.number="cols" :disabled="isRunning" />
              </div>
            </div>
          </div>

          <div class="row">
            <div class="group-l">
              <label>地图生成</label>
              <!-- 调整为三行：1) 演示地图 2) 障碍密度滑块 3) 随机/清除按钮 -->
              <div class="inline">
                <button class="btn" @click="loadDemo" :disabled="isRunning">演示地图</button>
              </div>
              <div class="inline">
                <input type="range" min="0" max="100" v-model.number="randomDensity" :disabled="isRunning" />
                <span class="muted">障碍密度 {{ randomDensity }}%</span>
              </div>
              <div class="inline">
                <button class="btn" @click="randomizeObstacles" :disabled="isRunning">随机障碍</button>
                <button class="btn ghost" @click="clearObstacles" :disabled="isRunning">清除障碍</button>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="group-l">
              <label>编辑模式</label>
              <div class="inline">
                <label class="radio"><input type="radio" value="start" v-model="editMode" :disabled="isRunning" />
                  起点</label>
                <label class="radio"><input type="radio" value="end" v-model="editMode"
                    :disabled="isRunning || isCoverage" /> 终点</label>
                <label class="radio"><input type="radio" value="wall" v-model="editMode" :disabled="isRunning" />
                  障碍</label>
                <label class="radio"><input type="radio" value="erase" v-model="editMode" :disabled="isRunning" />
                  橡皮擦</label>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="group-l">
              <label>算法选择</label>
              <div class="inline">
                <zSelect v-model="category" :options="categoryOptions" placeholder="选择类别" :disabled="isRunning"
                  style="min-width:120px" />

                <zSelect v-model="algorithm" :options="algorithmOptions" placeholder="选择算法" :disabled="isRunning"
                  style="min-width:150px" />
              </div>
            </div>
          </div>
        </div>
        <!-- 移动过来的工具栏 -->
        <div class="toolbar pathplanner-card">
          <div class="row space-between">
            <div class="group-r">
              <button class="btn primary" @click="run" :disabled="!canRun || isRunning">运行</button>
              <button class="btn danger" @click="stop" :disabled="!isRunning">停止</button>
              <button class="btn" @click="clearVisited" :disabled="isRunning">清除轨迹</button>
              <button class="btn ghost" @click="resetAll" :disabled="isRunning">重置</button>
            </div>
            <div class="group-r">
              <input type="range" min="1" max="200" v-model.number="speed" :disabled="isRunning" />
              <span class="muted">速度{{ speed }} ms / 步</span>
              <span class="muted" v-if="message">{{ message }}</span>
            </div>
          </div>
        </div>
      </aside>

      <!-- 右侧：工具条 + 画布 + 说明 -->
      <main class="content">

        <!-- 矩阵地图 -->
        <div class="grid-wrapper pathplanner-card">
          <div class="grid-inner">
            <div class="grid" :style="{ gridTemplateColumns: 'repeat(' + cols + ', var(--cell-size))' }"
              @mouseleave="dragging = false">
              <div v-for="cell in flatGrid" :key="cell.row + '-' + cell.col" class="cell" :class="cellClass(cell)"
                @mousedown.prevent="onCellDown(cell)" @mouseenter.prevent="onCellEnter(cell)"
                @mouseup.prevent="onCellUp(cell)">
                <span v-if="cell.type === 'start'" class="badge">S</span>
                <span v-else-if="cell.type === 'end'" class="badge">E</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 说明 -->
        <div class="pathplanner-card">
          <h2>使用提示</h2>
          <ul>
            <li>左侧选择“起点/终点/障碍/橡皮擦”，在右侧地图点选或拖动进行编辑。</li>
            <li>“全覆盖”算法不需要终点，会从起点出发尽可能覆盖所有可达网格。</li>
            <li>点击上方“运行”开始可视化，过程中可“停止”，结束后可“清除轨迹”。</li>
          </ul>
        </div>
      </main>
    </div>
  </div>

</template>

<script setup lang="ts">
import { computed, reactive, ref, watch } from 'vue'
import {zSelect} from '@/components/z-ui/select/zSelect'

type CellType = 'empty' | 'wall' | 'start' | 'end'
type Pos = { row: number; col: number }
type Cell = Pos & {
  type: CellType
  visited: boolean
  inPath: boolean
}

// 可调参数
const rows = ref(20)
const cols = ref(30)
const speed = ref(20) // ms per step
const randomDensity = ref(25)
const editMode = ref<'start' | 'end' | 'wall' | 'erase'>('start')
const category = ref<'shortest' | 'coverage'>('shortest')
const algorithm = ref('bfs')

// 选项数据
const categoryOptions = [
  { value: 'shortest', label: '最短路径' },
  { value: 'coverage', label: '全覆盖' },
]

const algorithmOptions = computed(() => {
  return category.value === 'shortest'
    ? [
      { value: 'bfs', label: 'BFS（无权最短）' },
      { value: 'dijkstra', label: 'Dijkstra' },
      { value: 'astar', label: 'A*' },
    ]
    : [
      { value: 'dfs_coverage', label: 'DFS 全覆盖' },
      { value: 'bfs_coverage', label: 'BFS 全覆盖' },
    ]
})

const isRunning = ref(false)
const dragging = ref(false)
const dragAction = ref<'wall' | 'erase' | null>(null)
const message = ref('')

// 地图网格
const grid = reactive<Cell[][]>([])
const startPos = ref<Pos | null>(null)
const endPos = ref<Pos | null>(null)

// 初始化 & 重建矩阵
function createGrid(r: number, c: number) {
  grid.length = 0
  for (let i = 0; i < r; i++) {
    const row: Cell[] = []
    for (let j = 0; j < c; j++) {
      row.push({ row: i, col: j, type: 'empty', visited: false, inPath: false })
    }
    grid.push(row)
  }
}

createGrid(rows.value, cols.value)

// 监听尺寸变化，自动重建（保持矩阵严格矩形）
let resizing = false
watch([rows, cols], () => {
  if (isRunning.value) return // 运行中不允许动态变化
  if (resizing) return
  resizing = true
  const r = Math.max(1, Math.min(60, rows.value))
  const c = Math.max(1, Math.min(80, cols.value))
  if (r !== rows.value) rows.value = r
  if (c !== cols.value) cols.value = c
  const oldStart = startPos.value
  const oldEnd = endPos.value
  createGrid(r, c)
  startPos.value = null
  endPos.value = null
  if (oldStart && oldStart.row < r && oldStart.col < c) setStart(oldStart)
  if (!isCoverage.value && oldEnd && oldEnd.row < r && oldEnd.col < c) setEnd(oldEnd)
  clearVisited()
  resizing = false
})

watch(category, (val) => {
  if (val === 'coverage') {
    // 选择 coverage 时自动切换算法并禁用终点
    algorithm.value = algorithm.value.includes('coverage') ? algorithm.value : 'dfs_coverage'
  } else {
    algorithm.value = ['bfs', 'dijkstra', 'astar'].includes(algorithm.value) ? algorithm.value : 'bfs'
  }
})

const flatGrid = computed(() => grid.flat())

const isCoverage = computed(() => category.value === 'coverage')
const canRun = computed(() => {
  if (isCoverage.value) return !!startPos.value
  return !!startPos.value && !!endPos.value
})

// resizeGrid 按钮已移除，逻辑由 watch 自动处理

function clearVisited() {
  for (const row of grid) {
    for (const cell of row) {
      cell.visited = false
      cell.inPath = false
    }
  }
  message.value = ''
}

function clearObstacles() {
  for (const row of grid) for (const cell of row) if (cell.type === 'wall') cell.type = 'empty'
}

function resetAll() {
  createGrid(rows.value, cols.value)
  startPos.value = null
  endPos.value = null
  message.value = ''
}

function randomizeObstacles() {
  clearVisited()
  clearObstacles()
  const density = Math.max(0, Math.min(100, randomDensity.value)) / 100
  for (const row of grid) {
    for (const cell of row) {
      if (Math.random() < density) cell.type = 'wall'
    }
  }
  // 不要把起终点覆盖
  if (startPos.value) grid[startPos.value.row][startPos.value.col].type = 'start'
  if (endPos.value) grid[endPos.value.row][endPos.value.col].type = 'end'
}

function loadDemo() {
  // 一个简单的演示地图：中间回字形障碍
  resetAll()
  const r = rows.value
  const c = cols.value
  const margin = 2
  for (let i = margin; i < r - margin; i++) {
    for (let j = margin; j < c - margin; j++) {
      const onRing = i === margin || i === r - margin - 1 || j === margin || j === c - margin - 1
      if (onRing) grid[i][j].type = 'wall'
    }
  }
  // 在墙上开几个门
  if (r > 8 && c > 12) {
    grid[margin][Math.floor(c / 2)].type = 'empty'
    grid[Math.floor(r / 2)][margin].type = 'empty'
    grid[r - margin - 1][Math.floor(c / 2)].type = 'empty'
  }
  setStart({ row: 1, col: 1 })
  if (!isCoverage.value) setEnd({ row: r - 2, col: c - 2 })
}

// 单元格交互
function setStart(pos: Pos) {
  if (!inBounds(pos)) return
  if (endPos.value && samePos(endPos.value, pos)) return
  if (grid[pos.row][pos.col].type === 'wall') return
  if (startPos.value) {
    const old = startPos.value
    grid[old.row][old.col].type = 'empty'
  }
  startPos.value = { ...pos }
  grid[pos.row][pos.col].type = 'start'
}

function setEnd(pos: Pos) {
  if (!inBounds(pos)) return
  if (startPos.value && samePos(startPos.value, pos)) return
  if (grid[pos.row][pos.col].type === 'wall') return
  if (isCoverage.value) return
  if (endPos.value) {
    const old = endPos.value
    grid[old.row][old.col].type = 'empty'
  }
  endPos.value = { ...pos }
  grid[pos.row][pos.col].type = 'end'
}

function onCellDown(cell: Cell) {
  if (isRunning.value) return
  if (editMode.value === 'start') setStart(cell)
  else if (editMode.value === 'end') setEnd(cell)
  else if (editMode.value === 'wall') {
    if (cell.type === 'start' || cell.type === 'end') return
    cell.type = 'wall'
    dragging.value = true
    dragAction.value = 'wall'
  } else if (editMode.value === 'erase') {
    if (cell.type === 'start') startPos.value = null
    if (cell.type === 'end') endPos.value = null
    cell.type = 'empty'
    dragging.value = true
    dragAction.value = 'erase'
  }
}

function onCellEnter(cell: Cell) {
  if (!dragging.value || isRunning.value) return
  if (dragAction.value === 'wall') {
    if (cell.type === 'start' || cell.type === 'end') return
    cell.type = 'wall'
  } else if (dragAction.value === 'erase') {
    if (cell.type === 'start') startPos.value = null
    if (cell.type === 'end') endPos.value = null
    cell.type = 'empty'
  }
}

function onCellUp(_cell: Cell) {
  dragging.value = false
  dragAction.value = null
}

function cellClass(cell: Cell) {
  return {
    wall: cell.type === 'wall',
    start: cell.type === 'start',
    end: cell.type === 'end' && !isCoverage.value,
    visited: cell.visited,
    path: cell.inPath,
  }
}

// 工具方法
function inBounds(p: Pos) {
  return p.row >= 0 && p.row < rows.value && p.col >= 0 && p.col < cols.value
}
function samePos(a: Pos, b: Pos) {
  return a.row === b.row && a.col === b.col
}
function neighbors(p: Pos): Pos[] {
  const dirs = [
    { row: -1, col: 0 },
    { row: 1, col: 0 },
    { row: 0, col: -1 },
    { row: 0, col: 1 },
  ]
  const res: Pos[] = []
  for (const d of dirs) {
    const np = { row: p.row + d.row, col: p.col + d.col }
    if (inBounds(np) && grid[np.row][np.col].type !== 'wall') res.push(np)
  }
  return res
}

// 算法实现
type RunResult = { visitedOrder: Pos[]; path?: Pos[] }

function bfsShortest(start: Pos, end: Pos): RunResult {
  const q: Pos[] = [start]
  const seen = new Set<string>([key(start)])
  const parent = new Map<string, Pos>()
  const visitedOrder: Pos[] = []
  while (q.length) {
    const cur = q.shift()!
    visitedOrder.push(cur)
    if (samePos(cur, end)) break
    for (const nb of neighbors(cur)) {
      const k = key(nb)
      if (!seen.has(k)) {
        seen.add(k)
        parent.set(k, cur)
        q.push(nb)
      }
    }
  }
  const path = reconstructPath(parent, start, end)
  return { visitedOrder, path }
}

function dijkstra(start: Pos, end: Pos): RunResult {
  // 网格均匀权重 -> 等同 BFS，但保留通用结构
  const dist = new Map<string, number>()
  const parent = new Map<string, Pos>()
  const visitedOrder: Pos[] = []
  const pq: Array<{ p: Pos; d: number }> = []

  function push(item: { p: Pos; d: number }) {
    pq.push(item)
    pq.sort((a, b) => a.d - b.d)
  }

  const startKey = key(start)
  dist.set(startKey, 0)
  push({ p: start, d: 0 })

  const seen = new Set<string>()
  while (pq.length) {
    const { p, d } = pq.shift()!
    const k = key(p)
    if (seen.has(k)) continue
    seen.add(k)
    visitedOrder.push(p)
    if (samePos(p, end)) break
    for (const nb of neighbors(p)) {
      const nk = key(nb)
      const nd = d + 1
      if (dist.get(nk) === undefined || nd < dist.get(nk)!) {
        dist.set(nk, nd)
        parent.set(nk, p)
        push({ p: nb, d: nd })
      }
    }
  }

  const path = reconstructPath(parent, start, end)
  return { visitedOrder, path }
}

function astar(start: Pos, end: Pos): RunResult {
  const h = (p: Pos) => Math.abs(p.row - end.row) + Math.abs(p.col - end.col)
  const g = new Map<string, number>()
  const f = new Map<string, number>()
  const parent = new Map<string, Pos>()
  const open: Array<{ p: Pos; f: number }> = []
  const visitedOrder: Pos[] = []

  function push(item: { p: Pos; f: number }) {
    open.push(item)
    open.sort((a, b) => a.f - b.f)
  }

  const sk = key(start)
  g.set(sk, 0)
  f.set(sk, h(start))
  push({ p: start, f: h(start) })
  const openSet = new Set<string>([sk])
  const closed = new Set<string>()

  while (open.length) {
    const { p } = open.shift()!
    const pk = key(p)
    openSet.delete(pk)
    if (closed.has(pk)) continue
    closed.add(pk)
    visitedOrder.push(p)
    if (samePos(p, end)) break
    for (const nb of neighbors(p)) {
      const nk = key(nb)
      if (closed.has(nk)) continue
      const tentative = g.get(pk)! + 1
      if (g.get(nk) === undefined || tentative < g.get(nk)!) {
        parent.set(nk, p)
        g.set(nk, tentative)
        const nf = tentative + h(nb)
        f.set(nk, nf)
        if (!openSet.has(nk)) {
          push({ p: nb, f: nf })
          openSet.add(nk)
        }
      }
    }
  }
  const path = reconstructPath(parent, start, end)
  return { visitedOrder, path }
}

function dfsCoverage(start: Pos): RunResult {
  const visitedOrder: Pos[] = []
  const seen = new Set<string>()
  function dfs(p: Pos) {
    const k = key(p)
    if (seen.has(k)) return
    seen.add(k)
    visitedOrder.push(p)
    for (const nb of neighbors(p)) dfs(nb)
  }
  dfs(start)
  // 对于覆盖路径，我们把访问顺序当作行走路径
  return { visitedOrder, path: visitedOrder.slice() }
}

function bfsCoverage(start: Pos): RunResult {
  const q: Pos[] = [start]
  const seen = new Set<string>([key(start)])
  const visitedOrder: Pos[] = []
  while (q.length) {
    const cur = q.shift()!
    visitedOrder.push(cur)
    for (const nb of neighbors(cur)) {
      const k = key(nb)
      if (!seen.has(k)) {
        seen.add(k)
        q.push(nb)
      }
    }
  }
  return { visitedOrder, path: visitedOrder.slice() }
}

function key(p: Pos) {
  return `${p.row},${p.col}`
}

function reconstructPath(parent: Map<string, Pos>, start: Pos, end: Pos): Pos[] | undefined {
  const res: Pos[] = []
  const ek = key(end)
  if (!parent.has(ek) && !samePos(start, end)) return undefined
  let cur: Pos | undefined = end
  while (cur && !samePos(cur, start)) {
    res.push(cur)
    cur = parent.get(key(cur))
  }
  res.push(start)
  res.reverse()
  return res
}

// 可视化执行
async function run() {
  if (!canRun.value || !startPos.value) return
  isRunning.value = true
  message.value = ''
  clearVisited()

  const s = startPos.value
  let result: RunResult | null = null
  const algo = algorithm.value
  try {
    if (isCoverage.value) {
      if (algo === 'dfs_coverage') result = dfsCoverage(s)
      else result = bfsCoverage(s)
    } else {
      const e = endPos.value!
      if (algo === 'astar') result = astar(s, e)
      else if (algo === 'dijkstra') result = dijkstra(s, e)
      else result = bfsShortest(s, e)
    }
  } catch (e) {
    console.error(e)
    message.value = '运行失败：' + (e as Error).message
    isRunning.value = false
    return
  }

  if (!result) {
    message.value = '无结果'
    isRunning.value = false
    return
  }

  // 动画访问顺序
  for (const p of result.visitedOrder) {
    if (!isRunning.value) return // 被停止
    grid[p.row][p.col].visited = true
    await sleep(speed.value)
  }

  // 最短路径或覆盖轨迹
  if (result.path && result.path.length) {
    for (const p of result.path) {
      if (!isRunning.value) return
      grid[p.row][p.col].inPath = true
      await sleep(Math.max(10, Math.floor(speed.value / 2)))
    }
  } else if (!isCoverage.value) {
    message.value = '未找到从起点到终点的路径'
  }

  isRunning.value = false
}

function stop() {
  isRunning.value = false
}

function sleep(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}

// 让起/终点与类别联动
watch(isCoverage, (v) => {
  if (v) {
    // 覆盖模式下移除终点标记
    if (endPos.value) {
      const e = endPos.value
      if (inBounds(e) && grid[e.row][e.col].type === 'end') grid[e.row][e.col].type = 'empty'
      endPos.value = null
    }
    if (!algorithm.value.includes('coverage')) algorithm.value = 'dfs_coverage'
    if (editMode.value === 'end') editMode.value = 'start'
  } else {
    if (!['bfs', 'dijkstra', 'astar'].includes(algorithm.value)) algorithm.value = 'bfs'
  }
})

</script>

<style scoped>
.pathplanner-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem;
  background-color: #fff;
}

.pathplanner-header {
  text-align: center;
  margin-bottom: 1.5rem;
}

.pathplanner-title {
  font-size: 2.2rem;
  font-weight: 700;
  color: #24292f;
  margin-bottom: 0.5rem;
  line-height: 1.2;
}

.pathplanner-subtitle {
  font-size: 1rem;
  color: #656d76;
  line-height: 1.6;
  max-width: 760px;
  margin: 0 auto;
}

.pathplanner-card {
  background: white;
  border: 1px solid #d1d9e1;
  padding: 1rem 1.25rem;
  transition: all 0.2s ease;
}

.pathplanner-card+.pathplanner-card {
  margin-top: 1rem;
}

.pathplanner-card:hover {
  border-color: #0969da;
  box-shadow: 0 4px 12px rgba(9, 105, 218, 0.06);
}

/* 布局 */
.layout {
  display: grid;
  grid-template-columns: minmax(260px, 340px) 1fr;
  gap: 1rem;
  align-items: start;
}

.sidebar {
  position: sticky;
  top: 8rem;
  align-self: start;
}

.content {
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.toolbar {
  /* 现在位于侧边栏内部，不再需要 sticky 定位 */
  position: static;
  margin-top: 1rem;
}

/* 控制面板 */
.controls {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
}

.row {
  display: flex;
  flex-wrap: wrap;
  gap: 1rem 1.25rem;
  align-items: center;
}

.row.space-between {
  justify-content: space-between;
}

.group-l,
.group-r {
  gap: 0.75rem;
  align-items: center;
  flex-wrap: wrap;
}

.group-l {
  display: grid;
  gap: 0.75rem;
  align-items: center;
  flex-wrap: wrap;
}

.group-r {
  display: flex;
  gap: 0.75rem;
  align-items: center;
  flex-wrap: wrap;
}

.group-l>label,.group-r>label {
  font-weight: 600;
  color: #24292f;
}

.inline {
  display: inline-flex;
  gap: 0.5rem;
  align-items: center;
  flex-wrap: wrap;
}

input[type='number'] {
  width: 80px;
  padding: 0.35rem 0.5rem;
  border: 1px solid #c9d1d9;
  border-radius: 6px;
}

select {
  padding: 0.4rem 0.5rem;
  border: 1px solid #c9d1d9;
  border-radius: 6px;
  background: #fff;
}

input[type='range'] {
  accent-color: #0969da;
}

.radio {
  display: inline-flex;
  gap: 0.35rem;
  align-items: center;
}

.muted {
  color: #656d76;
  font-size: 0.92rem;
}

/* 原生按钮样式 */
.btn {
  padding: 0.45rem 0.75rem;
  border: 1px solid #c9d1d9;
  background: #f6f8fa;
  color: #24292f;
  border-radius: 6px;
  cursor: pointer;
  transition: background-color 0.15s ease, filter 0.15s ease, opacity 0.15s ease;
}

.btn:hover {
  background: #eef2f6;
}

.btn.primary {
  border-color: #0969da;
  background: #0969da;
  color: #fff;
}

.btn.primary:hover {
  filter: brightness(0.95);
}

.btn.danger {
  border-color: #dc2626;
  background: #dc2626;
  color: #fff;
}

.btn.danger:hover {
  filter: brightness(0.95);
}

.btn.ghost {
  background: transparent;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}


/* 地图网格 */
.grid-wrapper {
  overflow: auto;
}

.grid-inner {
  width: max-content;
  padding: 4px;
}

.grid {
  --cell-size: 26px;
  display: grid;
  gap: 2px;
  align-items: center;
  justify-content: start;
}

.cell {
  width: var(--cell-size);
  height: var(--cell-size);
  border-radius: 4px;
  border: 1px solid #e6ebf1;
  background: #f8fafc;
  position: relative;
  box-sizing: border-box;
  transition: background-color 0.1s ease, transform 0.1s ease, border-color 0.1s ease;
}

.cell:hover {
  transform: scale(1.02);
}

.cell.wall {
  background: #0f172a;
  border-color: #0f172a;
}

.cell.start {
  background: #22c55e22;
  border-color: #16a34a;
}

.cell.end {
  background: #ef444422;
  border-color: #ef4444;
}

.cell.visited {
  background: #93c5fd55;
}

.cell.path {
  background: #22c55e88;
}

.badge {
  position: absolute;
  right: 4px;
  top: 2px;
  font-size: 0.75rem;
  font-weight: 700;
  color: #0f172a;
}

/* 说明样式 */
.pathplanner-card h2 {
  margin: 0 0 0.5rem 0;
  font-size: 1.1rem;
  font-weight: 600;
  color: #24292f;
}

.pathplanner-card ul {
  margin: 0;
  padding-left: 1.4rem;
}

.pathplanner-card li {
  margin-bottom: 0.35rem;
  color: #24292f;
  line-height: 1.6;
}

@media (max-width: 768px) {
  .pathplanner-container {
    padding: 1rem;
  }

  .pathplanner-title {
    font-size: 1.75rem;
  }

  .layout {
    grid-template-columns: 1fr;
  }

  .sidebar,
  .toolbar {
    position: static;
  }

  .grid {
    --cell-size: 22px;
    gap: 1.5px;
  }
}
</style>