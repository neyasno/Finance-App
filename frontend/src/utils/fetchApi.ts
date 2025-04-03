import { getCookie } from './cookie';

type RequestMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

const fetchApi = async (path: string, method: RequestMethod, body = {}) => {
  const token = getCookie('token');

  let response: Response;

  console.log(path);

  if (method === 'GET') {
    response = await fetch(path, {
      method,
      headers: {
        authorization: `Bearer ${token}`,
      },
    });
  } else {
    response = await fetch(path, {
      method,
      headers: {
        authorization: `Bearer ${token}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });
  }

  if (!response.ok) {
    throw new Error(`${response.status}`);
  }

  if (response.status == 204) return;

  return await response.json();
};

export default fetchApi;
