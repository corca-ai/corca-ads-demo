<script setup>
import PhotoGrid from "~/components/PhotoGrid.vue";
import { useFetchAdsSuggestion } from "~/composables/useFetchAdsSuggestion";

// 상품 리스트는 보리보리에 맞게 CSR에서 데이터를 fetch합니다.
const { data: photos, pending } = await useFetchAdsSuggestion("photos", {
  server: false, // CSR fetch
  params: {
    _limit: 20,
  },
});
</script>

<template>
  <div className="w-full flex flex-col items-center gap-4 p-12 bg-gray-100">
    <div v-if="pending">
      <div>Loading...</div>
    </div>
    <div v-else>
      <PhotoGrid :photos="photos" />
    </div>
  </div>
</template>
