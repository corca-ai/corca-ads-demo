<script setup>
import { useNuxtApp, callWithNuxt } from "#app";
import { useFetchAdsSuggestion } from "~/composables/useFetchAdsSuggestion";
import { definePageMeta } from "#imports";

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
onMounted(() => {
  // TODO: PageView API POST
  console.log("PageView API POST");
});

// 장바구니 버튼을 클릭하면 장바구니 API를 호출합니다.
const handleAddToCart = async (photoId) => {
  console.log(`Add to Cart API POST: ${photoId}`);
};

// 구매 버튼을 클릭하면 구매 API를 호출합니다.
const handlePurchase = async (photoId) => {
  console.log(`Purchase API POST: ${photoId}`);
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
            @click="handleAddToCart(photo.id)"
            class="bg-blue-500 text-white px-4 py-2 rounded-lg hover:bg-blue-600 transition"
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
            @click="handlePurchase(photo.id)"
            class="bg-green-500 text-white px-4 py-2 rounded-lg hover:bg-green-600 transition"
          >
            구매
          </button>
        </NuxtLink>
      </div>
    </div>
  </div>
</template>
