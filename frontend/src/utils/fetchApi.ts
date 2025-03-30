type RequestMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';

const fetchApi = async (path: string, method: RequestMethod, body = {}) => {
  const token = localStorage.getItem('token');

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

  return response.json();
};

export default fetchApi;
