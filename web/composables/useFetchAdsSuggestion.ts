export const useFetchAdsSuggestion: typeof useFetch = (request, opts?) => {
  return useFetch(request, {
    baseURL: "http://localhost:8080/api/",
    ...opts,
  });
};
