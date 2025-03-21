'use client';

import Button from '@/app/_components/common/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/TextInput';
import { EApi, ERoutes } from '@/enums';
import { Link, useRouter } from '@/i18n/routing';
import { setIsLogined } from '@/store/slices/userSlice';
import { useAppDispatch } from '@/store/store';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import { useState } from 'react';

export default function Page() {
  const t = useTranslations('verification.login');

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [isLoanding, setIsLoanding] = useState(false);
  const [error, setError] = useState('');

  const router = useRouter();
  const dispatcher = useAppDispatch();

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();
    console.log(`Send form: ${email}, ${password}`);

    try {
      setIsLoanding(true);
      setError('');

      const response = await fetchApi(EApi.LOGIN, 'POST', { email, password });
      localStorage.setItem('token', response.token);

      dispatcher(setIsLogined(true));

      router.push(ERoutes.DEFAULT);
    } catch (err) {
      if (typeof err === 'object' && 'message' in err! && 'stack' in err!) {
        if (err.message == '409') {
          setError(t('err_409'));
        } else {
          setError(t('err_500'));
        }
      }
    } finally {
      setIsLoanding(false);
    }
  };

  return (
    <div className="w-full flex items-center justify-center flex-col mt-10">
      <div className="flex flex-col justify-items-center w-10/12 max-w-md gap-4 items-center">
        <h1 className="text-3xl">{t('login')}</h1>

        <TextInput
          placeholder={t('email')}
          value={email}
          handleChange={setEmail}
        />

        <TextInput
          isPassword
          placeholder={t('password')}
          value={password}
          handleChange={setPassword}
        />

        <div className="w-28">
          {isLoanding ? (
            <Loading />
          ) : (
            <Button text={t('sing_in')} handleClick={sendForm} />
          )}
        </div>

        <div className="flex gap-1">
          <p>{t('no_account')}</p>
          <Link href={ERoutes.REGISTRATION}>
            <p className="hover:underline text-link">{t('create')}</p>
          </Link>
        </div>

        <p className="text-warn">{error}</p>
      </div>
    </div>
  );
}
