<template>
  <div class="absolute inset-0 overflow-hidden"
    style="transform:translateZ(0);transform-origin:0% 0%;"><img alt="Who We Are page background with space theme"
      aria-hidden="true" loading="eager" decoding="async" data-nimg="fill"
      style="position:absolute;height:100%;width:100%;left:0;top:0;right:0;bottom:0;object-fit:cover;color:transparent"
      src="https://kurzgesagt.org/_next/static/media/who-we-are_background.17ca4a9d.svg"><img
      alt="Background gradient overlay for visual effect" aria-hidden="true" loading="eager" decoding="async"
      data-nimg="fill" style="position:absolute;height:100%;width:100%;left:0;top:0;right:0;bottom:0;color:transparent"
      src="https://kurzgesagt.org/_next/static/media/background-gradient.d978676c.svg"><img
      alt="Stars overlay pattern in background" aria-hidden="true" loading="eager" decoding="async" data-nimg="fill"
      style="position:absolute;height:100%;width:100%;left:0;top:0;right:0;bottom:0;object-fit:cover;color:transparent"
      src="https://kurzgesagt.org/_next/static/media/background-stars-overlay.d476870e.svg">
    <div class="relative mx-auto h-full w-full max-w-[1440px] px-6 sm:px-10 lg:px-16"><img
        alt="Clouds overlay pattern in background" aria-hidden="true" loading="eager" decoding="async" data-nimg="fill"
        style="position:absolute;height:100%;width:100%;left:0;top:0;right:0;bottom:0;object-fit:cover;color:transparent"
        src="https://kurzgesagt.org/_next/static/media/background-clouds-overlay.1a853003.svg">
      <div class="absolute inset-0 translate-y-20">
        <img
        alt="Decorative cloud formations in the background" aria-hidden="true" loading="eager" width="2427"
        height="1503" decoding="async" data-nimg="1"
        class="absolute -left-[min(8.255vw,7.4295rem)] -top-[min(2.95vw,2.655rem)] w-[min(125vw,112.5rem)] max-w-[125vw] mobile:-left-[60.5vw] mobile:w-[250vw] mobile:max-w-[250vw]"
        style="color:transparent" src="https://kurzgesagt.org/_next/static/media/clouds-stars.d64c771f.svg"><img
        alt="Decorative vertical line element" aria-hidden="true" loading="eager" width="10" height="537"
        decoding="async" data-nimg="1"
        class="absolute right-[min(30.05vw,27.045rem)] top-[min(1.5vw,1.35rem)] w-[min(0.5vw,0.45rem)] animate-stretch-vertical-loop mobile:right-[4.1vw] mobile:top-[5vw] mobile:w-[1vw]"
        style="color:transparent" src="https://kurzgesagt.org/_next/static/media/vertical-line.77d2df7b.svg"><img
        alt="Decorative horizontal line element" aria-hidden="true" loading="eager" width="355" height="10"
        decoding="async" data-nimg="1"
        class="absolute right-[min(20vw,18rem)] top-[min(13.7vw,12.33rem)] w-[min(20vw,18rem)] animate-stretch-horizontal-loop mobile:-right-[11vw] mobile:top-[30.3vw] mobile:w-[32vw]"
        style="color:transparent" src="https://kurzgesagt.org/_next/static/media/horizontal-line.49db1da0.svg"><img
        alt="Decorative dot connecting the lines" aria-hidden="true" loading="eager" width="19" height="19"
        decoding="async" data-nimg="1"
        class="absolute right-[min(29.76vw,26.784rem)] top-[min(13.35vw,12.015rem)] h-[min(1.1vw,0.99rem)] w-[min(1.1vw,0.99rem)] mobile:right-[3.575vw] mobile:top-[29.7vw] mobile:h-[2.1vw] mobile:w-[2.1vw]"
        style="color:transparent" src="https://kurzgesagt.org/_next/static/media/middle-dot.a868fd82.svg"><img
        alt="Secondary decorative vertical line element" aria-hidden="true" loading="eager" width="11" height="135"
        decoding="async" data-nimg="1"
        class="absolute right-[min(34.85vw,31.365rem)] top-[min(7.05vw,6.345rem)] w-[min(0.55vw,0.495rem)] animate-stretch-vertical-loop mobile:right-[13.75vw] mobile:top-[17.55vw] mobile:w-[1vw]"
        style="color:transparent;animation-delay:500ms"
        src="https://kurzgesagt.org/_next/static/media/star-vertical.7f37c124.svg"><img
        alt="Secondary decorative horizontal line element" aria-hidden="true" loading="eager" width="246" height="11"
        decoding="async" data-nimg="1"
        class="absolute right-[min(29.3vw,26.37rem)] top-[min(10.3vw,9.27rem)] w-[min(11.5vw,10.35rem)] animate-stretch-horizontal-loop mobile:right-[4vw] mobile:top-[23.6vw] mobile:w-[20vw]"
        style="color:transparent;animation-delay:500ms"
        src="https://kurzgesagt.org/_next/static/media/star-horizontal.04eb44ca.svg"><img
        alt="Secondary decorative dot connecting the lines" aria-hidden="true" loading="eager" width="17" height="17"
        decoding="async" data-nimg="1"
        class="absolute right-[min(34.5vw,31.05rem)] top-[min(9.925vw,8.9325rem)] h-[min(1.2vw,1.08rem)] w-[min(1.2vw,1.08rem)] mobile:right-[13.1vw] mobile:top-[22.85vw] mobile:h-[2.35vw] mobile:w-[2.35vw]"
        style="color:transparent" src="https://kurzgesagt.org/_next/static/media/star-highlight.32fe6ada.svg"></div></div>
  </div>
  <div class="title">EXPLORE</div>
</template>

<script setup lang="ts">
</script>

<style scoped>
.title{
  font-family: 'Arial Black', sans-serif;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: 6rem;
  color: white;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
  pointer-events: none;
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
