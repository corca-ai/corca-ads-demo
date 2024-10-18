<script setup>
import { ref, useTemplateRef, defineProps } from "vue";
import { useIntersectionObserver } from "@vueuse/core";
import PhotoCard from "~/components/PhotoCard.vue";
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";

const INTERSECTION_THRESHOLD = 0.5;
const INTERSECTION_TIMER = 1000;

const props = defineProps({
  adsProducts: {
    type: {
      suggestions: [
        {
          product: {
            id: "string",
            name: "string",
            price: "number",
            discountPrice: "number",
            image: "string",
            summary: "string",
          },
          logOptions: {
            requestId: "string",
            adsetId: "string",
          },
        },
      ],
      placement: {
        id: "string",
        title: "string",
        displayCount: "number",
        activated: "boolean",
      },
    },
    required: true,
  },
});

const photosRef = useTemplateRef("photos");
const impressionIds = ref(new Set());

// console.log(props.adsProducts.suggestions[0].product);

/**
 * @description
 * 노출 버튼 클릭시 호출할 함수
 * 코르카 Ads의 impression 이벤트 로깅 API를 호출합니다.
 */
const handleImpression = async (id) => {
  await useAdsEventLogger("impression", {
    body: {
      customerId: "click을 한 유저 id",
      requestId: "suggestion에서 받은 requestId",
      productIdOnStore: id,
      adsetId: "Product ID / Banner ID",
      userAgent: "유저의 User Agent",
    },
  });
};

/**
 * @description
 * 코르카 ads 정책상, 아이템이 viewport에 1초가량 impression된 상품만 이벤트 로그 impression API를 호출합니다.
 * - 한 번 본 상품은 다시 1초 가량 impression되어도 API를 호출하지 않습니다.
 * - 관찰 대상의 50%가 뷰포트에 보이면 노출되었다고 봅니다.
 */
const observePhotos = () => {
  props.photos.forEach((photo, index) => {
    const photoRef = photosRef.value[index];
    let intersectionTimer = null;

    useIntersectionObserver(
      photoRef,
      ([{ isIntersecting }]) => {
        if (isIntersecting) {
          intersectionTimer = setTimeout(() => {
            if (isIntersecting && !impressionIds.value.has(photo.id)) {
              handleImpression(photo.id).then(() => {
                impressionIds.value.add(photo.id);
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
      v-for="photo in props.adsProducts.suggestions"
      :id="`photo-${photo.id}`"
      :key="photo.id"
      ref="photos"
      class="w-full h-full bg-white p-4 rounded-lg shadow-md transform transition-transform duration-200 hover:scale-105 hover:shadow-lg"
    >
      <PhotoCard :photo="photo" />
    </div>
  </div>
</template>
