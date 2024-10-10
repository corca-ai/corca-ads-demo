<script setup>
defineProps(["photo", "isLoaded"]);

const handleClick = async () => {
  // TODO: Click API POST
  // await useFetchWithBaseUrl(`/photos/${id}`, {
  //   method: "POST",
  // });
  console.log("Click API POST");
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
