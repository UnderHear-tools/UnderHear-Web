import BlankslateRoot from './zBlankslate.vue'
import BlankslateVisual from './zBlankslateVisual.vue'
import BlankslateHeading from './zBlankslateHeading.vue'
import BlankslateDescription from './zBlankslateDescription.vue'
import BlankslatePrimaryAction from './zBlankslatePrimaryAction.vue'
import BlankslateSecondaryAction from './zBlankslateSecondaryAction.vue'

export const Blankslate = Object.assign(BlankslateRoot, {
  Visual: BlankslateVisual,
  Heading: BlankslateHeading,
  Description: BlankslateDescription,
  PrimaryAction: BlankslatePrimaryAction,
  SecondaryAction: BlankslateSecondaryAction
})

export const zBlankslate = Blankslate
