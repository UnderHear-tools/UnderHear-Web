import DialogRoot from './Dialog.vue'
import DialogBody from './DialogBody.vue'
import DialogFooter from './DialogFooter.vue'

export const Dialog = Object.assign(DialogRoot, {
  Body: DialogBody,
  Footer: DialogFooter
})

export { DialogBody, DialogFooter }
export type {
  DialogCloseGesture,
  DialogSize
} from './Dialog.vue'
