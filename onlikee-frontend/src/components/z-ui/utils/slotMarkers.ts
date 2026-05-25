import { Comment, Fragment, Text, type VNode } from 'vue'

interface SlotMarkerType {
  name?: string
  __name?: string
}

export type SlotMarkerChildren<T extends string> = Record<T, VNode[]> & {
  default: VNode[]
}

export const RenderNodes = (props: { nodes: VNode[] }) => props.nodes

function getComponentName(type: VNode['type']) {
  if (typeof type !== 'object' && typeof type !== 'function') return ''

  const marker = type as SlotMarkerType
  return marker.name ?? marker.__name ?? ''
}

function readSlotChildren(node: VNode) {
  if (typeof node.children === 'object' && node.children && 'default' in node.children) {
    const slot = node.children.default
    return typeof slot === 'function' ? slot() : []
  }

  return []
}

function isWhitespaceNode(node: VNode) {
  return node.type === Text && typeof node.children === 'string' && node.children.trim() === ''
}

function flattenChildren(nodes: VNode[]): VNode[] {
  return nodes.flatMap(node => {
    if (node.type === Fragment && Array.isArray(node.children)) {
      return flattenChildren(node.children as VNode[])
    }

    return [node]
  })
}

export function parseSlotMarkers<T extends string>(
  nodes: VNode[],
  markers: Record<T, string>
): SlotMarkerChildren<T> {
  const markerEntries = Object.entries(markers) as [T, string][]
  const parsed = { default: [] } as Record<T | 'default', VNode[]>

  for (const [key] of markerEntries) {
    parsed[key] = []
  }

  for (const node of flattenChildren(nodes)) {
    if (node.type === Comment || isWhitespaceNode(node)) continue

    const componentName = getComponentName(node.type)
    const marker = markerEntries.find(([, name]) => name === componentName)

    if (marker) {
      parsed[marker[0]].push(...readSlotChildren(node))
      continue
    }

    parsed.default.push(node)
  }

  return parsed as SlotMarkerChildren<T>
}
