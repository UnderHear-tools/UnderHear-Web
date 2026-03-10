import FormControlRoot from './zFormControl.vue'
import FormControlLabel from './zFormControlLabel.vue'
import FormControlCaption from './zFormControlCaption.vue'
import FormControlValidation from './zFormControlValidation.vue'

export const FormControl = Object.assign(FormControlRoot, {
  Label: FormControlLabel,
  Caption: FormControlCaption,
  Validation: FormControlValidation
})

export const zFormControl = FormControl
export const zFormControlLabel = FormControlLabel
export const zFormControlCaption = FormControlCaption
export const zFormControlValidation = FormControlValidation
