// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2024-04-03",
  devtools: { enabled: true },
  runtimeConfig: {
    public: {
      storeId: "7bbb703e-a30b-4a4a-91b4-c0a7d2303415",
    },
  },
  modules: ["@nuxtjs/tailwindcss"],
});
