export function addUnit(value?: string | number): string | undefined {
  if (value === undefined || value === null) return undefined
  if (typeof value === 'string') return value
  return `${value}px`
}
