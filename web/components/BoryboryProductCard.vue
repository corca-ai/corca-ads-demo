<script setup>
defineProps(["product"]);
</script>

<template>
  <div>
    <div v-if="isLoaded">loading</div>
    <div
      v-else
      class="w-full h-full bg-white p-4 rounded-lg shadow-md transform transition-transform duration-200 hover:scale-105 hover:shadow-lg"
    >
      <NuxtLink :to="`products/${product.id}`">
        <img
          :src="`${product.image}`"
          alt="보리보리 상품 이미지"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ product.name }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
