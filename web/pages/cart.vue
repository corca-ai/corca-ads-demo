<template>
  <div
    class="w-full h-screen flex flex-col items-center justify-center bg-gray-50 p-4"
  >
    <div
      class="w-full max-w-lg flex flex-col items-center bg-white p-6 rounded-lg shadow-md gap-4"
    >
      <div class="text-lg font-semibold text-gray-800">
        장바구니에 정상적으로 상품이 등록되었습니다!
      </div>
      <div class="text-gray-600">
        상품 ID: <span class="font-medium">{{ $route.query.productId }}</span>
      </div>
      <div class="text-gray-600">
        수량: <span class="font-medium">{{ $route.query.quantity }}</span>
      </div>
      <NuxtLink :to="'/'">
        <button
          class="mt-4 bg-blue-500 text-white px-6 py-2 rounded-lg hover:bg-blue-600 transition"
        >
          홈페이지로 가기
        </button>
      </NuxtLink>
    </div>
  </div>
</template>
