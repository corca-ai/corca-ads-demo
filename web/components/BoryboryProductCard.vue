<script setup>
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
const props = defineProps({
  product: {
    type: {
      id: Number,
      title: String,
      url: String,
    },
    required: true,
  },
  isLoaded: Boolean,
});
</script>

<template>
  <div>
    <div v-if="isLoaded">loading</div>
    <div v-else>
      <NuxtLink :to="`products/${props.product.id}`">
        <img
          :src="`${props.product.image}`"
          alt="보리보리 상품 이미지"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ props.product.name }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
