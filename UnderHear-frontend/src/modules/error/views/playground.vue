<script setup lang="ts">
import { ref } from 'vue'
import { FormControl } from '@/components/z-ui/form-control'
import { zInput } from '@/components/z-ui/input'
import { zTextarea } from '@/components/z-ui/textarea'

const appName = ref('UnderHear')
const appDescription = ref('A compact demo that shows how the compound FormControl API is composed.')
</script>

<template>
  <main class="playground-page">
    <section class="playground-card">
      <header class="playground-header">
        <h1>FormControl Playground</h1>
        <p>Namespaced compound usage with <code>&lt;FormControl.Label&gt;</code>, <code>&lt;FormControl.Caption&gt;</code> and <code>&lt;FormControl.Validation&gt;</code>.</p>
      </header>

      <div class="playground-stack">
        <FormControl class="playground-control">
          <FormControl.Label>Application Name</FormControl.Label>
          <zInput v-model="appName" />
          <FormControl.Caption>
            Keep the title short and readable.
          </FormControl.Caption>
          <FormControl.Validation variant="success">
            Name is available.
          </FormControl.Validation>
        </FormControl>

        <FormControl class="playground-control">
          <FormControl.Label>Description</FormControl.Label>
          <zTextarea
            v-model="appDescription"
            rows="5"
          />
          <FormControl.Caption>
            This block is optional helper text.
          </FormControl.Caption>
          <FormControl.Validation variant="error">
            Something went wrong.
          </FormControl.Validation>
        </FormControl>
      </div>
    </section>
  </main>
</template>

<style scoped>

</style>
