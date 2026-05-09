<script setup lang="ts">
withDefaults(
  defineProps<{
    border?: boolean
    narrow?: boolean
    spacious?: boolean
    size?: 'small' | 'medium' | 'large'
  }>(),
  {
    border: false,
    narrow: false,
    spacious: false,
    size: 'medium'
  }
)
</script>

<template>
  <section
    class="z-blankslate-container"
  >
    <div
      class="z-blankslate"
      :class="{
        'z-blankslate--border': border,
        'z-blankslate--narrow': narrow,
        'z-blankslate--spacious': spacious
      }"
      :data-size="size"
    >
      <slot />
    </div>
  </section>
</template>

<style scoped>
.z-blankslate-container {
  container-type: inline-size;
  min-width: 0;
  width: 100%;
}

.z-blankslate {
  --z-blankslate-max-width: 520px;
  --z-blankslate-padding-block: 2rem;
  --z-blankslate-padding-inline: 2rem;
  --z-blankslate-gap: 0.75rem;
  --z-blankslate-visual-size: 32px;
  --z-blankslate-heading-size: 1.5rem;
  --z-blankslate-heading-line-height: 1.333;
  --z-blankslate-description-size: 0.875rem;
  --z-blankslate-action-margin-top: 0.5rem;
  --z-blankslate-secondary-margin-top: 0.25rem;

  align-items: center;
  color: var(--fgColor-default, #1f2328);
  display: flex;
  flex-direction: column;
  gap: var(--z-blankslate-gap);
  margin-inline: auto;
  max-width: var(--z-blankslate-max-width);
  padding: var(--z-blankslate-padding-block) var(--z-blankslate-padding-inline);
  text-align: center;
  width: 100%;
}

.z-blankslate--border {
  border: 1px solid var(--borderColor-default, #d1d9e0);
  border-radius: var(--borderRadius-medium, 0.375rem);
}

.z-blankslate--narrow {
  --z-blankslate-max-width: 320px;
  --z-blankslate-padding-block: 1.5rem;
  --z-blankslate-padding-inline: 1rem;
  --z-blankslate-gap: 0.5rem;
  --z-blankslate-visual-size: 24px;
  --z-blankslate-heading-size: 1.25rem;
  --z-blankslate-description-size: 0.8125rem;
}

.z-blankslate--spacious {
  --z-blankslate-padding-block: 4rem;
  --z-blankslate-padding-inline: 2rem;
  --z-blankslate-gap: 1rem;
}

.z-blankslate[data-size='small'] {
  --z-blankslate-max-width: 360px;
  --z-blankslate-padding-block: 1.5rem;
  --z-blankslate-padding-inline: 1rem;
  --z-blankslate-gap: 0.5rem;
  --z-blankslate-visual-size: 24px;
  --z-blankslate-heading-size: 1.25rem;
  --z-blankslate-description-size: 0.8125rem;
}

.z-blankslate[data-size='large'] {
  --z-blankslate-max-width: 620px;
  --z-blankslate-padding-block: 3rem;
  --z-blankslate-padding-inline: 2rem;
  --z-blankslate-gap: 1rem;
  --z-blankslate-visual-size: 40px;
  --z-blankslate-heading-size: 1.75rem;
}

@container (max-width: 360px) {
  .z-blankslate {
    --z-blankslate-padding-block: 1.5rem;
    --z-blankslate-padding-inline: 1rem;
    --z-blankslate-gap: 0.5rem;
    --z-blankslate-visual-size: 24px;
    --z-blankslate-heading-size: 1.25rem;
    --z-blankslate-description-size: 0.8125rem;
  }
}
</style>
