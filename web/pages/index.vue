<script setup>
import ProductGrid from "~/components/ProductGrid.vue";
import { useGlobalDeviceId, useGlobalSessionId } from "~/store/ads";

const config = useRuntimeConfig();
const storeId = config.public.storeId;

const deviceId = useGlobalDeviceId();
const sessionId = useGlobalSessionId();

const { data: adsProducts, pending: adsPending } = await useFetch(
  "corca-ads/products",
  {
    baseURL: "http://localhost:8080/api/",
    server: false,
    params: {
      placementId: "0eae4a71-a99f-44db-8aea-4a8d7e06fc41",
      sessionId: sessionId,
      deviceId: deviceId,
    },
  }
);

const { data: boryboryProducts, pending: boryboryPending } = useFetch(
  "products",
  {
    baseURL: "http://localhost:8080/api/",
    server: false,
  }
);
</script>

<template>
  <div className="w-full flex flex-col items-center gap-4 p-12 bg-gray-100">
    <div v-if="adsPending || boryboryPending">
      <div>Loading...</div>
    </div>
    <div v-else>
      <ProductGrid
        :adsProducts="adsProducts"
        :boryboryProducts="boryboryProducts"
      />
    </div>
  </div>
</template>
