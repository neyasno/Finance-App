'use client';

import Button from '@/app/_components/common/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/TextInput';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import { useSearchParams } from 'next/navigation';
import React, { useState } from 'react';

export default function RestorePasswordForm() {
  const t = useTranslations('verification.restore_password');
  const params = useSearchParams();

  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState('');

  const sendForm = async (e: React.MouseEvent<HTMLElement, MouseEvent>) => {
    e.preventDefault();

    try {
      setIsLoading(true);

      setError('');

      const res = await fetchApi(EApi.RESTORE_PASSWORD, 'POST', {
        password,
        token: params.get('token'),
      });

      console.log(res);
    } catch (err) {
      console.log(err);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      <h1 className="text-3xl">{t('restore_password')}</h1>

      <TextInput
        isPassword
        placeholder={t('password')}
        value={password}
        handleChange={setPassword}
      />

      <TextInput
        isPassword
        placeholder={t('confirm_password')}
        value={confirmPassword}
        handleChange={setConfirmPassword}
      />

      <div className="w-28">
        {isLoading ? (
          <Loading />
        ) : (
          <Button text={t('send')} handleClick={sendForm} />
        )}
      </div>

      <p className="text-warn">{error}</p>
    </>
  );
}
