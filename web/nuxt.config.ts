// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2024-04-03",
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      storeId: "7a5c6244-fb4b-4962-87d0-47e63bfee377", // TODO: 보리보리 계정 id 생기면 그걸로 변경
    },
  },
  modules: ["@nuxtjs/tailwindcss"],
});
