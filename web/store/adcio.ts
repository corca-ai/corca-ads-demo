import { createGlobalState, useSessionStorage, useStorage } from "@vueuse/core";
import { useRuntimeConfig } from "#app";

export const useGlobalDeviceId = createGlobalState(() => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;

  return useStorage(`adcio-device-${storeId}`, createUUID());
});

export const useGlobalSessionId = createGlobalState(() => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;
  const storageKey = `adcio-session-${storeId}`;
  const expirationTime = 30 * 60 * 1000;

  return useSessionStorage(storageKey, () => {
    const currentTime = Date.now();
    const sessionExpiration =
      Number(sessionStorage.getItem(`${storageKey}-expiration`)) || 0;

    if (currentTime > sessionExpiration) {
      const newSessionId = createUUID();
      sessionStorage.setItem(
        `${storageKey}-expiration`,
        String(currentTime + expirationTime)
      );
      return newSessionId;
    }

    return sessionStorage.getItem(storageKey) || createUUID();
  });
});

const createUUID = () => crypto.randomUUID();
