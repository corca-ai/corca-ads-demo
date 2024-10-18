<script setup>
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
const props = defineProps({
  photo: {
    type: {
      id: Number,
      title: String,
      url: String,
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
      customerId: "click을 한 유저 id",
      requestId: "suggestion에서 받은 requestId",
      productIdOnStore: "상품 id",
      adsetId: "Product ID / Banner ID",
      userAgent: "유저의 User Agent",
    },
  });
};
</script>

<template>
  <div>
    <div v-if="isLoaded">loading</div>
    <div v-else @click="handleClick">
      <NuxtLink :to="`products/${props.photo.product.id}`">
        <img
          :src="`${props.photo.product.image}`"
          alt="Photo Avatar"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ props.photo.product.name }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
