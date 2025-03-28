'use client';

import { EApi, ERoutes } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import React, { ReactNode, useEffect, useState } from 'react';
import Loading from '../../common/Loading';
import { useAppDispatch } from '@/store/store';
import { setIsLogined } from '@/store/slices/userSlice';
import { useRouter } from '@/i18n/routing';
import { useLogin } from '@/utils/hooks/useLogin';

export default function VerificationWrapper({
  children,
}: {
  children: ReactNode;
}) {
  const [isLoading, setIsLoading] = useState(true);

  const { setLogined } = useLogin();

  useEffect(() => {
    const verifyRequest = async () => {
      await fetchApi(EApi.VERIFICATION, 'GET');
    };

    verifyRequest()
      .then(() => {
        setLogined(true);

        setIsLoading(false);
      })
      .catch((err) => {
        console.log('Verification error : ' + err);

        setIsLoading(false);
      });
  }, [setLogined]);

  if (isLoading) {
    return (
      <div className="w-full h-min-screen flex items-center p-10">
        <Loading />
      </div>
    );
  } else {
    return <div className="h-full">{children}</div>;
  }
}
