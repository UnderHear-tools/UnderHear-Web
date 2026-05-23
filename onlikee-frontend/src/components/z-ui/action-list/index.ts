import ActionListRoot from './ActionList.vue'
import ActionListItem from './ActionListItem.vue'
import ActionListLinkItem from './ActionListLinkItem.vue'
import ActionListDivider from './ActionListDivider.vue'
import ActionListGroup from './ActionListGroup.vue'
import ActionListGroupHeading from './ActionListGroupHeading.vue'

export const ActionList = Object.assign(ActionListRoot, {
  Item: ActionListItem,
  LinkItem: ActionListLinkItem,
  Divider: ActionListDivider,
  Group: ActionListGroup,
  GroupHeading: ActionListGroupHeading
})

export { ActionListDivider, ActionListGroup, ActionListGroupHeading, ActionListItem, ActionListLinkItem }
export type {
  ActionListDescriptionVariant,
  ActionListItemSize,
  ActionListItemVariant,
  ActionListSelectionVariant,
  ActionListVariant
} from './context'
