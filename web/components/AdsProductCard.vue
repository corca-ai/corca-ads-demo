<script setup>
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
const props = defineProps(["product", "logOptions"]);

/**
 * @description
 * 상품을 클릭 시 호출할 함수
 * 코르카 Ads의 click 이벤트 로깅 API를 호출합니다.
 *
 * API 설명: https://corcaai.notion.site/123dd8f2aea280fd862dff19637510ba#123dd8f2aea28131be86ca6f35662e61
 */
const handleClick = async () => {
  await useAdsEventLogger("click", {
    body: {
      requestId: props.logOptions.requestId,
      adsetId: props.logOptions.adsetId,
      userAgent: navigator.userAgent,
    },
  });
};
</script>

<template>
  <div>
    <div v-if="isLoaded">loading</div>
    <div
      v-else
      @click="handleClick"
      class="w-full h-full bg-white p-4 rounded-lg shadow-md transform transition-transform duration-200 hover:scale-105 hover:shadow-lg"
    >
      <NuxtLink
        :to="{
          path: `products/${props.product.id}`,
          query: {
            productId: props.product.id,
          },
        }"
      >
        <div
          class="absolute top-2 right-2 bg-green-500 text-white px-3 py-1 text-xs rounded-full shadow-md"
        >
          코르카 광고
        </div>
        <img
          :src="`${props.product.image}`"
          alt="코르카 광고 상품 이미지"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ props.product.name }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
