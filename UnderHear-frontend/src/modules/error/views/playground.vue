<script setup lang="ts">
import { Timeline } from '@/components/z-ui/timeline'

</script>

<template>
  <main class="playground-page">
    <section class="playground-card">
      <Timeline>
        <Timeline.Item>
          <Timeline.Badge>
            1
          </Timeline.Badge>
          <Timeline.Body>
            <div>6666</div>
            <div>6666</div>
            <div>6666</div>
            <div>6666</div>
            <div>6666</div>
            <div>6666</div>
            <div>6666</div>
          </Timeline.Body>
        </Timeline.Item>
        <Timeline.Item>
          <Timeline.Badge>
            1
          </Timeline.Badge>
          <Timeline.Body>
            666666
          </Timeline.Body>
        </Timeline.Item>
        <Timeline.Item>
          <Timeline.Badge>
            1
          </Timeline.Badge>
          <Timeline.Body>
            666666
          </Timeline.Body>
        </Timeline.Item>
      </Timeline>
    </section>
  </main>
</template>

<style scoped>
.playground-page {
  min-height: 100%;
  padding: 24px;
  display: flex;
  align-items: flex-start;
  justify-content: center;
  background: var(--bgColor-muted, #f6f8fa);
}

.playground-card {
  width: min(100%, 540px);
  padding: 16px;
  border: 1px solid var(--borderColor-default, #d1d9e0);
  border-radius: 8px;
  background: var(--bgColor-default, #ffffff);
}
</style>
