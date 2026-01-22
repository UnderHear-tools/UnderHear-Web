export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

export const buildApiUrl = (path: string) => new URL(path, API_BASE_URL).toString();
