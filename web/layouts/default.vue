<template>
  <div>
    <div class="w-full bg-white p-3 shadow-lg relative">
      <NuxtLink :to="'/'">
        <button type="button" class="flex gap-2">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            class="w-5 h-5 transition"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-7 0a1 1 0 001 1h4a1 1 0 001-1m-6 0V9m0 11h6"
            />
          </svg>
          <span>Home</span>
        </button>
      </NuxtLink>
    </div>
    <slot />
  </div>
</template>
