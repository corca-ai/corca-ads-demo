import { useGlobalDeviceId, useGlobalSessionId } from "~/store/adcio";

// 이벤트 로깅은 CSR에서 코르카 Ads의 API를 요청해야 합니다.
export const useAdsEventLogger: typeof useFetch = (request, opts?) => {
  const config = useRuntimeConfig();
  const storeId = config.public.storeId;

  if (process.server) {
    throw new Error(
      "deviceId와 sessionId가 스토리지에 저장되어 있기에, SSR일 때는 사용할 수 없습니다."
    );
  }

  const deviceId = useGlobalDeviceId();
  const sessionId = useGlobalSessionId();

  return useFetch(request, {
    ...opts,
    baseURL: "https://receiver.adcio.ai/events/",
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
