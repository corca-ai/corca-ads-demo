<script setup>
import { ref, useTemplateRef, defineProps } from "vue";
import { useIntersectionObserver } from "@vueuse/core";
import AdsProductCard from "~/components/AdsProductCard.vue";
import { useAdsEventLogger } from "~/composables/useAdsEventLogger";

const INTERSECTION_THRESHOLD = 0.5;
const INTERSECTION_TIMER = 1000;

const props = defineProps(["adsProducts", "boryboryProducts"]);

const adsProductsRef = useTemplateRef("adsProducts");
const impressionIds = ref(new Set());

/**
 * @description
 * 노출 버튼 클릭시 호출할 함수
 * 코르카 Ads의 impression 이벤트 로깅 API를 호출합니다.
 *
 * API 설명: https://www.notion.so/corcaai/123dd8f2aea280fd862dff19637510ba?pvs=4#123dd8f2aea28126a2d4caa5f185f3eb
 */
const handleImpression = async (logOptions) => {
  await useAdsEventLogger("impression", {
    body: {
      requestId: logOptions.requestId,
      adsetId: logOptions.adsetId,
      userAgent: navigator.userAgent,
    },
  });
};

/**
 * @description
 * 코르카 ads 정책상, 아이템이 viewport에 1초가량 impression된 상품만 이벤트 로그 impression API를 호출합니다.
 * - 한 번 본 상품은 다시 1초 가량 impression되어도 API를 호출하지 않습니다.
 * - 관찰 대상의 50%가 뷰포트에 보이면 노출되었다고 봅니다.
 */
const observeAdsProducts = () => {
  props.adsProducts.suggestions.forEach((suggestion, index) => {
    const adsProductRef = adsProductsRef.value[index];
    let intersectionTimer = null;

    useIntersectionObserver(
      adsProductRef,
      ([{ isIntersecting }]) => {
        if (isIntersecting) {
          intersectionTimer = window.setTimeout(() => {
            if (
              isIntersecting &&
              !impressionIds.value.has(suggestion.product.id)
            ) {
              handleImpression(suggestion.logOptions).then(() => {
                impressionIds.value.add(suggestion.product.id);
              });
            }
          }, INTERSECTION_TIMER);
        } else {
          if (intersectionTimer !== null) {
            clearTimeout(intersectionTimer);
            intersectionTimer = null;
          }
        }
      },
      {
        threshold: INTERSECTION_THRESHOLD,
      }
    );
  });
};

onMounted(() => {
  observeAdsProducts();
});
</script>

<template>
  <div
    id="product-grid"
    class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 p-8 w-full max-w-7xl mx-auto"
  >
    <!-- Ads Products 렌더링 -->
    <div
      v-for="suggestion in props.adsProducts.suggestions"
      :key="suggestion.product.id"
      ref="adsProducts"
    >
      <AdsProductCard
        :product="suggestion.product"
        :logOptions="suggestion.logOptions"
      />
    </div>

    <!-- Borybory Products 렌더링 -->
    <div v-for="product in props.boryboryProducts.content" :key="product.id">
      <BoryboryProductCard :product="product" />
    </div>
  </div>
</template>
