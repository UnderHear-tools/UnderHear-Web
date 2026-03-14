import { inject, provide, type ComputedRef, type InjectionKey } from 'vue'

export type ActionListSelectionMode = 'none' | 'single'

export interface ActionListContext {
  selectionMode: ComputedRef<ActionListSelectionMode>
  modelValue: ComputedRef<string>
  select: (value: string) => void
}

const actionListContextKey: InjectionKey<ActionListContext> = Symbol('ActionListContext')

export function createContext(context: ActionListContext): ActionListContext {
  return context
}

export function provideContext(context: ActionListContext) {
  provide(actionListContextKey, context)
}

export function useContext() {
  return inject(actionListContextKey, null)
}
