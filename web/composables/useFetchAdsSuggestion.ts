export const useFetchAdsSuggestion: typeof useFetch = (request, opts?) => {
  const config = useRuntimeConfig();

  return useFetch(request, {
    baseURL: "https://jsonplaceholder.typicode.com/",
    ...opts,
  });
};
