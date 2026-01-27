<template>
  <div class="background-root">
    <img
      alt="Who We Are page background with space theme"
      class="background-0"
      src="../assests/background-0.svg"
    >
    <img
      alt="Background gradient overlay for visual effect"
      class="background-1"
      src="../assests/background-1.svg"
    >
    <img
      alt="Stars overlay pattern in background"
      class="background-2"
      src="../assests/background-2.svg"
    >
    <div class="relative mx-auto h-full w-full max-w-[1440px] px-6 sm:px-10 lg:px-16">
    <img
      alt="Clouds overlay pattern in background"
      class="background-3"
      src="../assests/background-3.svg"
    >
    <img
      alt="Decorative cloud formations in the background"
      aria-hidden="true"
      loading="eager"
      width="2427"
      height="1503"
      decoding="async"
      data-nimg="1"
      class="background-4"
      src="../assests/background-4.svg"
    >
    <img
      alt="Decorative vertical line element"
      class="background-5 animate-stretch-vertical-loop"
      src="../assests/background-5.svg"
    >
    <img
      alt="Decorative horizontal line element"
      class="background-6 animate-stretch-horizontal-loop"
      src="../assests/background-6.svg"
    >
    <img
      alt="Decorative dot connecting the lines"
      class="background-7"
      src="../assests/background-7.svg"
    >
    <img
      alt="Secondary decorative vertical line element"
      class="background-8 animate-stretch-vertical-loop"
      src="../assests/background-8.svg"
    >
    <img
      alt="Secondary decorative horizontal line element"
      class="background-9 animate-stretch-horizontal-loop"
      src="../assests/background-9.svg"
    >
    <img
      alt="Secondary decorative dot connecting the lines"
      class="background-10"
      src="../assests/background-10.svg"
    >
    </div>
    <div class="title">EXPLORE</div>
  </div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.background-root {
  position: absolute;
  inset: 0;
  overflow: hidden;
  transform: translateZ(0);
  transform-origin: 0% 0%;
  --bg-offset: 80px;
}

.background-0,
.background-1,
.background-2,
.background-3 {
  position: absolute;
  height: 100%;
  width: 100%;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  color: transparent;
}

.background-0 {
  z-index: 0;
}

.background-1 {
  z-index: 1;
}

.background-2 {
  z-index: 3;
}
.background-3 {
  z-index: 2;
}

.background-0,
.background-2, 
.background-3 {
  object-fit: cover;
}

.background-4 {
  position: absolute;
  left: calc(-1 * min(8.255vw, 7.4295rem));
  top: calc(-1 * min(2.95vw, 2.655rem) + var(--bg-offset));
  width: min(125vw, 112.5rem);
  max-width: 125vw;
  color: transparent;
  z-index: 4;
}

.background-5 {
  position: absolute;
  right: min(30.05vw, 27.045rem);
  top: calc(min(1.5vw, 1.35rem) + var(--bg-offset));
  width: min(0.5vw, 0.45rem);
  color: transparent;
  z-index: 5;
}

.background-6 {
  position: absolute;
  right: min(20vw, 18rem);
  top: calc(min(13.7vw, 12.33rem) + var(--bg-offset));
  width: min(20vw, 18rem);
  color: transparent;
  z-index: 6;
}

.background-7 {
  position: absolute;
  right: min(29.76vw, 26.784rem);
  top: calc(min(13.35vw, 12.015rem) + var(--bg-offset));
  height: min(1.1vw, 0.99rem);
  width: min(1.1vw, 0.99rem);
  color: transparent;
  z-index: 7;
}

.background-8 {
  position: absolute;
  right: min(34.85vw, 31.365rem);
  top: calc(min(7.05vw, 6.345rem) + var(--bg-offset));
  width: min(0.55vw, 0.495rem);
  color: transparent;
  animation-delay: 500ms;
  z-index: 8;
}

.background-9 {
  position: absolute;
  right: min(29.3vw, 26.37rem);
  top: calc(min(10.3vw, 9.27rem) + var(--bg-offset));
  width: min(11.5vw, 10.35rem);
  color: transparent;
  animation-delay: 500ms;
  z-index: 9;
}

.background-10 {
  position: absolute;
  right: min(34.5vw, 31.05rem);
  top: calc(min(9.925vw, 8.9325rem) + var(--bg-offset));
  height: min(1.2vw, 1.08rem);
  width: min(1.2vw, 1.08rem);
  color: transparent;
  z-index: 10;
}

.title {
  font-family: 'Arial Black', sans-serif;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 6rem;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  pointer-events: none;
  z-index: 2;
  font-weight: bold;
}
@keyframes stretch-vertical {
  0% {
    transform: scaleY(0);
  }
  100% {
    transform: scaleY(1);
  }
}

@keyframes stretch-horizontal {
  0% {
    transform: scaleX(0);
  }
  100% {
    transform: scaleX(1);
  }
}

@keyframes pulse-vertical {
  0%,
  100% {
    transform: scaleY(0.6);
  }
  50% {
    transform: scaleY(1);
  }
}

@keyframes pulse-horizontal {
  0%,
  100% {
    transform: scaleX(0.6);
  }
  50% {
    transform: scaleX(1);
  }
}

.animate-stretch-vertical {
  transform-origin: top;
  animation: stretch-vertical 1.2s cubic-bezier(0.22, 1, 0.36, 1) both;
}

.animate-stretch-vertical-loop {
  transform-origin: center;
  animation: pulse-vertical 1.6s ease-in-out infinite;
}

.animate-stretch-horizontal {
  transform-origin: left;
  animation: stretch-horizontal 1.2s cubic-bezier(0.22, 1, 0.36, 1) both;
}

.animate-stretch-horizontal-loop {
  transform-origin: center;
  animation: pulse-horizontal 1.6s ease-in-out infinite;
}

@media (max-width: 768px) {
  .title {
    font-size: 3.5rem;
  }
}
</style>
