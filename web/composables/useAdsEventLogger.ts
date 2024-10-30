import { useFetch } from "#app";
import { useGlobalDeviceId, useGlobalSessionId } from "~/store/ads";

/**
 * @description
 * 코르카 Ads의 이벤트 로깅 API를 호출하는 데 사용되는 컴포저블 함수입니다.
 * 이벤트 로깅 시 `storeId`, `deviceId`, `sessionId`는 필수로 요청 DTO에 포함되어야 합니다.
 * - 이벤트 로깅 API 주소: https://receiver.adcio.ai/events/
 */
export const useAdsEventLogger: typeof useFetch = (request, opts?) => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;

  if (import.meta.server) {
    throw new Error(
      "deviceId와 sessionId가 스토리지에 저장되어 있기에, SSR일 때는 사용할 수 없습니다."
    );
  }

  const deviceId = useGlobalDeviceId();
  const sessionId = useGlobalSessionId();

  return useFetch(request, {
    ...opts,
    baseURL: "https://receiver.corca.dev/v1/events/",
    method: "post",
    server: false,
    body: {
      storeId,
      deviceId,
      sessionId,
      ...(typeof opts?.body === "object" && opts.body !== null
        ? opts.body
        : {}),
    },
  });
};
