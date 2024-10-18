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
  logOptions: {
    type: {
      requestId: String,
      adsetId: String,
    },
    required: true,
  },
  isLoaded: Boolean,
});

// 상품을 클릭 시 Click 이벤트 API를 호출합니다.
const handleClick = async () => {
  // TODO: 백엔드 API 완성되면 정상적인 필드값 전달
  await useAdsEventLogger("click", {
    body: {
      requestId: props.logOptions.requestId,
      productIdOnStore: props.product.id,
      adsetId: props.logOptions.adsetId,
      userAgent: navigator.userAgent,
    },
  });
};
</script>

<template>
  <div>
    <div v-if="isLoaded">loading</div>
    <div v-else @click="handleClick">
      <NuxtLink
        :to="{
          path: `products/${props.product.id}`,
          query: {
            requestId: props.logOptions.requestId,
            adsetId: props.logOptions.adsetId,
          },
        }"
      >
        <img
          :src="`${props.product.image}`"
          alt="코르카 상품 이미지"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ props.product.name }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
