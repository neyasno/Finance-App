export function getCookie(name: string): string | null {
  const cookieString = document.cookie;
  const cookies = cookieString.split(';').map((cookie) => cookie.trim());

  for (const cookie of cookies) {
    if (cookie.startsWith(`${name}=`)) {
      return cookie.substring(name.length + 1);
    }
  }
  return null;
}

export function setCookie(
  name: string,
  value: string,
  days?: number,
  path: string = '/'
) {
  let expires = '';
  if (days) {
    const date = new Date();
    date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);

    expires = '; expires=' + date.toUTCString();
  }
  document.cookie = `${name}=${encodeURIComponent(value)}${expires}; path=${path}`;
}
