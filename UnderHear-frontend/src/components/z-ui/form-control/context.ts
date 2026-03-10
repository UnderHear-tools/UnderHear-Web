import { inject, provide, ref, type InjectionKey, type Ref } from 'vue'

export type FormControlValidationVariant = 'success' | 'error'

export interface FormControlContextValue {
  validationVariant: Ref<FormControlValidationVariant | null>
}

const formControlContextKey: InjectionKey<FormControlContextValue> = Symbol('FormControlContext')

export function createFormControlContext(): FormControlContextValue {
  return {
    validationVariant: ref<FormControlValidationVariant | null>(null)
  }
}

export function provideFormControlContext(context: FormControlContextValue) {
  provide(formControlContextKey, context)
}

export function useFormControlContext() {
  return inject(formControlContextKey, null)
}
