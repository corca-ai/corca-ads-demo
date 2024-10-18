import { useStorage } from "@vueuse/core";

export const useAdsLogOptions = () => {
  const logOptions = useStorage("ads-log-options", {}); // 기본값을 {}로 설정

  // 나중에 log options를 업데이트할 수 있는 함수
  const setLogOptions = (newOptions: {
    requestId: string;
    adsetId: string;
  }) => {
    logOptions.value = newOptions;
  };

  return {
    logOptions,
    setLogOptions,
  };
};
