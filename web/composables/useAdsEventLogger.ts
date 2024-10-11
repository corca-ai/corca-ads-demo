import { createStorage } from "~/utils/storage/storage.factory";
import { useFetchWithBaseUrl } from "./useFetchWithBaseUrl";

export const useAdsEventLogger: typeof useFetchWithBaseUrl = (
  request,
  opts?
) => {
  const config = useRuntimeConfig();
  const clientId = config.public.clientId;

  if (process.server) {
    throw new Error("SSR mode일 때는 사용할 수 없습니다.");
  }

  const deviceId = createStorage({
    local: { key: `adcio-device-${clientId}` },
  }).getOrSet();

  const sessionId = createStorage({
    session: {
      key: `adcio-session-${clientId}`,
      expiration: 30 * 60 * 1000, // 30분 유효 기간
    },
  }).getOrSet();

  const isPostRequest =
    opts?.method && "method" in opts && opts.method === "post";

  console.log({ request }, "????");

  return useFetchWithBaseUrl(request, {
    server: false,
    ...opts,
    ...(isPostRequest && {
      body: {
        clientId,
        deviceId,
        sessionId,
        ...(typeof opts.body === "object" && opts.body !== null
          ? opts.body
          : {}),
      },
    }),
  });
};
