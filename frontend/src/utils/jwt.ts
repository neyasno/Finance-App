import { JWTPayload, jwtVerify, SignJWT } from 'jose';

export const JWT_SECRET = process.env.JWT_SECRET || '319j9vhsd9he';

const jwtConfig = {
  secret: new TextEncoder().encode(JWT_SECRET),
};

export async function generateToken(payload: JWTPayload, expiresIn = '1 h') {
  const token = await new SignJWT(payload)
    .setProtectedHeader({ alg: 'HS256' })
    .setExpirationTime(expiresIn)
    .sign(new TextEncoder().encode(JWT_SECRET));

  console.log(token);

  return token;
}

export const isAuthenticated = async (req: Request) => {
  let token =
    req.headers.get('authorization') || req.headers.get('Authorization');

  if (token) {
    try {
      if (token.startsWith('Bearer')) {
        token = token.replace('Bearer ', '');

        console.log('Auth Token : ' + token);
      }

      const decoded = await jwtVerify(token, jwtConfig.secret);
      console.log('Decoded : ');

      console.log(decoded);

      if (decoded.payload?.email) {
        return decoded.payload?.email;
      } else {
        return '';
      }
    } catch (err) {
      console.error('isAuthenticated error: ', err);

      return '';
    }
  } else {
    return '';
  }
};
