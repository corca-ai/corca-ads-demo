import { createGlobalState, useSessionStorage, useStorage } from "@vueuse/core";
import { useRuntimeConfig } from "#app";

/**
 * @description
 * 코르카 ads 이벤트 로깅 시 request dto 필드로 전달해줄 deviceId를 전역 상태로 관리하는 컴포저블
 */
export const useGlobalDeviceId = createGlobalState(() => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;

  return useStorage(`ads-device-${storeId}`, createUUID());
});

const EXPIRATION_STORAGE = 30 * 60 * 1000; // session id 만료 시간: 30분
/**
 * @description
 * 코르카 ads 이벤트 로깅 시 request dto 필드로 전달해줄 sessionId를 전역 상태로 관리하는 컴포저블
 * 코르카 ads 정책상, sessionId는 30분이 지나면 만료시키고 재발급해야 합니다.
 */
export const useGlobalSessionId = createGlobalState(() => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;

  const storageKey = `ads-session-${storeId}`;
  const expirationKey = `${storageKey}-expiration`;

  const getSessionId = () => {
    // useSessionStorage를 사용하여 sessionId와 expiration을 관리
    const sessionIdStorage = useSessionStorage(storageKey, "");
    const expirationStorage = useSessionStorage(expirationKey, "");

    const currentTime = Date.now();
    const sessionExpiration = Number(expirationStorage.value) || 0;

    if (currentTime <= sessionExpiration && sessionIdStorage.value) {
      return sessionIdStorage.value;
    }

    // sessionId가 30분이 지나 만료되었거나 존재하지 않는 경우 새로 생성 및 저장
    const sessionId = createUUID();
    sessionIdStorage.value = sessionId;
    expirationStorage.value = String(currentTime + EXPIRATION_STORAGE);

    return sessionId;
  };

  return getSessionId();
});

const createUUID = () => crypto.randomUUID();
