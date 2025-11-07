import type { ClassValue } from "clsx"
import { clsx } from "clsx"
import { twMerge } from "tailwind-merge"

export function cn(...inputs: ClassValue[]) {
  return twMerge(clsx(inputs))
}

export function addUnit(value?: string | number): string | undefined {
  if (value === undefined || value === null) return undefined
  if (typeof value === 'string') return value
  return `${value}px`
}
