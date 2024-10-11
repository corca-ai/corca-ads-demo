<script setup>
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
defineProps(["photo", "isLoaded"]);

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
      <NuxtLink :to="`products/${photo.id}`">
        <img
          :src="`${photo.url}`"
          alt="Photo Avatar"
          width="500"
          height="500"
          class="mb-2"
        />
        <div>{{ photo.title }}</div>
      </NuxtLink>
    </div>
  </div>
</template>
