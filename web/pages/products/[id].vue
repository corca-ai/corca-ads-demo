<script setup>
import { callWithNuxt, useNuxtApp } from "#app";
import { definePageMeta, nextTick } from "#imports";
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
import { useFetchAdsSuggestion } from "~/composables/useFetchAdsSuggestion";

// Nuxt의 미들웨어를 사용하여 SSR에서 상품 디테일 데이터를 fetch합니다.
definePageMeta({
  middleware: [
    async (to) => {
      const nuxtApp = useNuxtApp();
      const { data: photo } = await useFetchAdsSuggestion(
        `photos/${to.params.id}`
      );
      await callWithNuxt(nuxtApp, () => {
        nuxtApp.$requestData = photo.value;
      });
    },
  ],
});

const nuxtApp = useNuxtApp();
const photo = ref(nuxtApp.$requestData);

// 페이지가 마운트되면 PageView 이벤트 API를 호출합니다.
onMounted(async () => {
  /**
   * @description
   * onMounted 후 API 요청 시 API 호출이 안 되는 이슈가 있어, nextTick을 이용해 다음 이벤트 루프 때 실행되게끔 구현했습니다.
   * 참고: https://stackoverflow.com/questions/76527094/nuxt-3-and-vue-3-onmounted-call-function-usefetch-function-not-getting-data-form
   */
  await nextTick();

  // TODO: 백엔드 API 완성되면 정상적인 필드값 전달
  await useAdsEventLogger("view", {
    body: {
      customerId: "click을 한 유저 id",
      requestId: "suggestion에서 받은 requestId",
      productIdOnStore: "상품 id",
      adsetId: "Product ID / Banner ID",
      categoryIdOnStore: "카테고리 ID",
      userAgent: "유저의 User Agent",
    },
  });
});

// 장바구니 버튼을 클릭하면 장바구니 API를 호출합니다.
const handleAddToCart = async (quantity) => {
  await useAdsEventLogger("add-to-cart", {
    body: {
      customerId: "click을 한 유저 id",
      requestId: "suggestion에서 받은 requestId",
      productIdOnStore: photo.value.id,
      adsetId: "Product ID / Banner ID",
      categoryIdOnStore: "카테고리 ID",
      cartId: "장바구니 ID",
      quantity,
      userAgent: "유저의 User Agent",
    },
  });
};

// 구매 버튼을 클릭하면 구매 API를 호출합니다.
const handlePurchase = async (quantity) => {
  await useAdsEventLogger("purchase", {
    body: {
      customerId: "click을 한 유저 id",
      requestId: "suggestion에서 받은 requestId",
      productIdOnStore: photo.value.id,
      adsetId: "Product ID / Banner ID",
      categoryIdOnStore: "카테고리 ID",
      cartId: "장바구니 ID",
      quantity,
      userAgent: "유저의 User Agent",
    },
  });
};
</script>

<template>
  <div class="flex items-center justify-center w-full h-screen p-8 bg-gray-100">
    <div
      class="flex flex-col items-center w-4/5 max-w-3xl bg-white p-6 rounded-lg shadow-md"
    >
      <img
        :src="`${photo.url}`"
        alt="Photo Avatar"
        width="100%"
        height="100%"
        class="w-full mb-4 rounded-md"
      />
      <div class="text-xl font-semibold mb-4">{{ photo.title }}</div>
      <div class="flex gap-4">
        <NuxtLink
          :to="{ path: '/cart', query: { productId: photo.id, quantity: 1 } }"
        >
          <button
            class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
            @click="handleAddToCart(quantity)"
          >
            장바구니
          </button>
        </NuxtLink>
        <NuxtLink
          :to="{
            path: '/purchase',
            query: { productId: photo.id, quantity: 1 },
          }"
        >
          <button
            class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition"
            @click="handlePurchase(quantity)"
          >
            구매
          </button>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>
