'use client';

import Button from '@/app/_components/common/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/TextInput';
import { EApi, ERoutes } from '@/enums';
import { Link } from '@/i18n/routing';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useState } from 'react';

export default function RestorePasswordReqForm() {
  const t = useTranslations('verification.restore_password_req');

  const [email, setEmail] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');

      await fetchApi(EApi.LOGIN, 'POST', { email });
    } catch (err) {
      console.log(err);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <h1 className="text-3xl">{t('restore_password_req')}</h1>

      <TextInput
        placeholder={t('email')}
        value={email}
        handleChange={setEmail}
      />

      <div className="w-28">
        {isLoading ? (
          <Loading />
        ) : (
          <Button text={t('send')} handleClick={sendForm} />
        )}
      </div>

      <div className="flex gap-1">
        <p>{t('had_account')}</p>
        <Link href={ERoutes.LOGIN}>
          <p className="hover:underline text-link">{t('sing_in')}</p>
        </Link>
      </div>

      <p className="text-warn">{error}</p>
    </>
  );
}
