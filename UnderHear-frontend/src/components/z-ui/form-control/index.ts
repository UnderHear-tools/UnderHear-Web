import FormControlRoot from './zFormControl.vue'
import FormControlLabel from './zFormControlLabel.vue'
import FormControlValidation from './zFormControlValidation.vue'
import FormControlCaption from './zFormControlCaption.vue'

export const FormControl = Object.assign(FormControlRoot, {
  Label: FormControlLabel,
  Validation: FormControlValidation,
  Caption: FormControlCaption
})

export const zFormControl = FormControl
export const zFormControlLabel = FormControlLabel
export const zFormControlValidation = FormControlValidation
export const zFormControlCaption = FormControlCaption
