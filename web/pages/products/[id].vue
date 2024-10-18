<script setup>
import { callWithNuxt, useNuxtApp } from "#app";
import { definePageMeta, nextTick } from "#imports";
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";
import { nanoid } from "nanoid";

// 보리보리와 동일하게, Nuxt의 미들웨어를 사용하여 SSR에서 상품 디테일 데이터를 fetch합니다.
definePageMeta({
  middleware: [
    async (to) => {
      const nuxtApp = useNuxtApp();
      const { data: productInfo } = await useFetch(`products/${to.params.id}`, {
        baseURL: "http://localhost:8080/api/",
      });
      await callWithNuxt(nuxtApp, () => {
        nuxtApp.$requestData = productInfo.value;
      });
    },
  ],
});

const nuxtApp = useNuxtApp();
const productInfo = ref(nuxtApp.$requestData);
const route = useRoute();

/**
 * @description
 * 페이지가 마운트되면 코르카 ads의 view 이벤트 로깅 API를 호출합니다.
 *
 * API 설명: https://www.notion.so/corcaai/123dd8f2aea280fd862dff19637510ba?pvs=4#123dd8f2aea281e789c5f8c6a0d284e4
 */
onMounted(async () => {
  await nextTick();

  await useAdsEventLogger("view", {
    body: {
      requestId: route.query.requestId,
      adsetId: route.query.adsetId,
      productIdOnStore: productInfo.value.id,
      userAgent: navigator.userAgent,
    },
  });
});

/**
 * @description
 * 장바구니 버튼 클릭시 호출할 함수
 * 코르카 Ads의 add-to-cart 이벤트 로깅 API를 호출합니다.
 *
 * API 설명: https://www.notion.so/corcaai/123dd8f2aea280fd862dff19637510ba?pvs=4#123dd8f2aea281adbdc1c3d35164e5dd
 */
const handleAddToCart = async (quantity) => {
  await useAdsEventLogger("add-to-cart", {
    body: {
      requestId: route.query.requestId,
      adsetId: route.query.adsetId,
      productIdOnStore: productInfo.value.id,
      quantity,
      userAgent: navigator.userAgent,
    },
  });
};

/**
 * @description
 * 구매 버튼 클릭시 호출할 함수
 * 코르카 Ads의 purchase 이벤트 로깅 API를 호출합니다.
 *
 * API 설명: https://www.notion.so/corcaai/123dd8f2aea280fd862dff19637510ba?pvs=4#123dd8f2aea2811c87fbf2b89910a2da
 */
const handlePurchase = async (quantity) => {
  await useAdsEventLogger("purchase", {
    body: {
      requestId: route.query.requestId,
      adsetId: route.query.adsetId,
      productIdOnStore: productInfo.value.id,
      quantity,
      orderId: nanoid(),
      amount: productInfo.value.price * quantity,
      userAgent: navigator.userAgent,
    },
  });
};
</script>

<template>
  <div class="flex items-center justify-center w-full h-screen bg-gray-100">
    <div
      class="flex flex-col items-center w-1/3 bg-white p-6 rounded-lg shadow-md"
    >
      <img
        :src="`${productInfo.image}`"
        alt="상품 상세 이미지"
        width="100%"
        height="100%"
        class="w-full mb-4 rounded-md"
      />
      <div class="text-xl font-semibold mb-4">{{ productInfo.name }}</div>
      <div class="flex gap-4">
        <NuxtLink
          :to="{
            path: '/cart',
            query: { productId: productInfo.id, quantity: 1 },
          }"
        >
          <button
            class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
            @click="handleAddToCart(1)"
          >
            장바구니
          </button>
        </NuxtLink>
        <NuxtLink
          :to="{
            path: '/purchase',
            query: { productId: productInfo.id, quantity: 1 },
          }"
        >
          <button
            class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition"
            @click="handlePurchase(1)"
          >
            구매
          </button>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>
