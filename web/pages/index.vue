<script setup>
import PhotoGrid from "~/components/PhotoGrid.vue";
import { useGlobalDeviceId, useGlobalSessionId } from "~/store/ads";

const config = useRuntimeConfig();
const storeId = config.public.storeId;

const deviceId = useGlobalDeviceId();
const sessionId = useGlobalSessionId();

const { data: photos, pending } = await useFetch("corca-ads/products", {
  baseURL: "http://localhost:8080/api/",
  server: false, // CSR fetch
  params: {
    clientId: storeId,
    placementId: "0eae4a71-a99f-44db-8aea-4a8d7e06fc41",
    sessionId: sessionId,
    deviceId: deviceId,
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
