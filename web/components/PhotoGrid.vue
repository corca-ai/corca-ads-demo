<script setup>
import { ref, useTemplateRef, defineProps } from "vue";
import { useIntersectionObserver } from "@vueuse/core";
import PhotoCard from "~/components/PhotoCard.vue";

const INTERSECTION_THRESHOLD = 0.5;
const INTERSECTION_TIMER = 1000;

const props = defineProps({
  photos: {
    type: Array,
    required: true,
  },
});

const photosRef = useTemplateRef("photos");
const exposedPhotoIds = ref(new Set());

// 노출 API를 호출하는 함수
const callExposureApi = async (photoId) => {
  console.log(`Exposure API POST: ${photoId}`);
};

// 아이템이 화면에 노출되었을 때 노출 API를 호출합니다.
// 이 때 1초동안 노출된 아이템만 노출 API를 호출하며, 이미 호출한 아이템은 다시 호출하지 않습니다.
const observePhotos = () => {
  props.photos.forEach((photo, index) => {
    const photoRef = photosRef.value[index];
    let intersectionTimer = null;

    useIntersectionObserver(
      photoRef,
      ([{ isIntersecting }]) => {
        if (isIntersecting) {
          intersectionTimer = setTimeout(() => {
            if (isIntersecting && !exposedPhotoIds.value.has(photo.id)) {
              callExposureApi(photo.id).then(() => {
                exposedPhotoIds.value.add(photo.id);
              });
            }
          }, INTERSECTION_TIMER);
        } else {
          clearTimeout(intersectionTimer);
        }
      },
      {
        threshold: INTERSECTION_THRESHOLD,
      }
    );
  });
};

onMounted(() => {
  observePhotos();
});
</script>

<template>
  <div
    id="photo-grid"
    class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-8 w-full max-w-7xl mx-auto"
  >
    <div
      v-for="photo in props.photos"
      :key="photo.id"
      :id="`photo-${photo.id}`"
      class="w-full h-full bg-white p-4 rounded-lg shadow-md transform transition-transform duration-200 hover:scale-105 hover:shadow-lg"
      ref="photos"
    >
      <PhotoCard :photo="photo" />
    </div>
  </div>
</template>
