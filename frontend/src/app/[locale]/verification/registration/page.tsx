'use client';

import Button from '@/app/_components/common/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/TextInput';
import { EApi, ERoutes } from '@/enums';
import { Link, useRouter } from '@/i18n/routing';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import { useState } from 'react';

export default function RegistrationPage() {
  const t = useTranslations('verification.registration');

  const router = useRouter();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [passwordAgain, setPasswordAgain] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');

      await fetchApi(EApi.REGISTRATION, 'POST', {
        email,
        password,
      });

      setIsLoading(false);

      router.push(ERoutes.LOGIN);
    } catch (err) {
      if (typeof err === 'object' && 'message' in err! && 'stack' in err!) {
        if (err.message == '409') {
          setError(t('err_409'));
        } else {
          setError(t('err_500'));
        }
        setIsLoading(false);
      }
    }
  };

  return (
    <>
      <h1 className="text-3xl">{t('registration')}</h1>

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

      <TextInput
        isPassword
        placeholder={t('confirm')}
        value={passwordAgain}
        handleChange={setPasswordAgain}
      />

      <div className="w-28">
        {isLoading ? (
          <Loading />
        ) : (
          <Button text={t('create')} handleClick={sendForm} />
        )}
      </div>

      <div className="flex gap-1">
        <p>{t('have_account')}</p>
        <Link href={ERoutes.LOGIN}>
          <p className="hover:underline text-link">{t('sing_in')}</p>
        </Link>
      </div>

      <div className="flex gap-1">
        <p>{t('forgot_password')}</p>
        <Link href={ERoutes.RESTORE_PASSWORD_REQ}>
          <p className="hover:underline text-link">{t('restore')}</p>
        </Link>
      </div>

      <p className="text-warn">{error}</p>
    </>
  );
}
