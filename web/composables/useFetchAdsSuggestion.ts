export const useFetchAdsSuggestion: typeof useFetch = (request, opts?) => {
  return useFetch(request, {
    baseURL: "https://jsonplaceholder.typicode.com/",
    ...opts,
  });
};
