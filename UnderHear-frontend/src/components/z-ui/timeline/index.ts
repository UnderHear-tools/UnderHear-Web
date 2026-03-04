import TimelineRoot from './zTimeline.vue'
import TimelineItem from './zTimelineItem.vue'
import TimelineBadge from './zTimelineBadge.vue'
import TimelineBody from './zTimelineBody.vue'

export const Timeline = Object.assign(TimelineRoot, {
  Item: TimelineItem,
  Badge: TimelineBadge,
  Body: TimelineBody
})

export const zTimeline = Timeline
export const zTimelineItem = TimelineItem
export const zTimelineBadge = TimelineBadge
export const zTimelineBody = TimelineBody
