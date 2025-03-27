'use client';

import { EApi, ERoutes } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import React, { ReactNode, useEffect, useState } from 'react';
import Loading from '../../common/Loading';
import { useAppDispatch } from '@/store/store';
import { setIsLogined } from '@/store/slices/userSlice';
import { useRouter } from '@/i18n/routing';

export default function VerificationWrapper({
  children,
}: {
  children: ReactNode;
}) {
  const [isLoanding, setIsLoanding] = useState(true);

  const router = useRouter();
  const dispatch = useAppDispatch();

  useEffect(() => {
    const verifyRequest = async () => {
      await fetchApi(EApi.VERIFICATION, 'GET');
    };

    verifyRequest()
      .then(() => {
        dispatch(setIsLogined(true));

        setIsLoanding(false);
      })
      .catch((err) => {
        console.log('Verification error : ' + err);

        router.push(ERoutes.LOGIN);

        setIsLoanding(false);
      });
  }, []);

  if (isLoanding) {
    return (
      <div className="w-full h-min-screen flex items-center p-10">
        <Loading />
      </div>
    );
  } else {
    return <div className="h-full">{children}</div>;
  }
}
