<script setup>
import PhotoGrid from "~/components/PhotoGrid.vue";

// CSR에서 상품 리스트 데이터를 fetch합니다.
const { data: photos, pending } = await useFetchWithBaseUrl("photos", {
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
