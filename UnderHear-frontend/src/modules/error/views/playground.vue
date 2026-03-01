<script setup lang="ts">
import { computed, ref } from 'vue'
import { zFormControl } from '@/components/z-ui/formControl'
import { zInput } from '@/components/z-ui/input'

const name = ref('Mona L!$a')
const hasInvalidChars = computed(() => /[^a-zA-Z\s]/.test(name.value))
</script>

<template>
  <main class="playground-page">
    <div class="demo-card">
      <zFormControl
        label="Name"
        html-for="playground-name"
        required
        :invalid="hasInvalidChars"
        validation="Names may not contain symbols"
        caption="This will be publicly visible"
      >
        <zInput id="playground-name" v-model="name" />
      </zFormControl>
    </div>
  </main>
</template>

<style scoped>
.playground-page {
  display: flex;
  justify-content: center;
  padding: 40px 16px;
}

.demo-card {
  width: min(100%, 520px);
  padding: 24px;
  border: 1px solid var(--borderColor-default);
  border-radius: 12px;
  background: var(--bgColor-default);
}
</style>
