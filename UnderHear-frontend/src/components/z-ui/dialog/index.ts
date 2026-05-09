import DialogRoot from './Dialog.vue'
import DialogBody from './DialogBody.vue'
import DialogFooter from './DialogFooter.vue'

export const Dialog = Object.assign(DialogRoot, {
  Body: DialogBody,
  Footer: DialogFooter
})

export const zDialog = Dialog

export { DialogBody, DialogFooter }
export type {
  DialogAlign,
  DialogCloseGesture,
  DialogHeight,
  DialogPosition,
  DialogSize
} from './Dialog.vue'
