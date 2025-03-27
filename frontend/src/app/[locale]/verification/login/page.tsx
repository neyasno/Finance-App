'use client';

import Button from '@/app/_components/common/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/TextInput';
import { EApi, ERoutes } from '@/enums';
import { Link, useRouter } from '@/i18n/routing';
import fetchApi from '@/utils/fetchApi';
import { useLogin } from '@/utils/hooks/useLogin';
import { useTranslations } from 'next-intl';
import { useState } from 'react';

export default function LoginPage() {
  const t = useTranslations('verification.login');

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const router = useRouter();
  const { setLogined } = useLogin();

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');

      const response = await fetchApi(EApi.LOGIN, 'POST', { email, password });
      localStorage.setItem('token', response.token);

      setLogined(true);

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
      setIsLoading(false);
    }
  };

  return (
    <>
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
        {isLoading ? (
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
    </>
  );
}
