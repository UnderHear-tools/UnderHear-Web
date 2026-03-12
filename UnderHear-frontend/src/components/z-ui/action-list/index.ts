import ActionListRoot from './zActionList.vue'
import ActionListItem from './zActionListItem.vue'

export const ActionList = Object.assign(ActionListRoot, {
  Item: ActionListItem
})

export const zActionList = ActionList
export const zActionListItem = ActionListItem
