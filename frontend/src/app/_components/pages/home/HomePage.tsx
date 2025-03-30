import { useLogin } from '@/utils/hooks/useLogin';
import React from 'react';
import ContentPage from './ContentPage';
import UnloginedPage from './UnloginedPage';

export default function HomePage() {
  const { isLogined } = useLogin();
  return (
    <div className="flex justify-center items-center px-2 pt-1">
      {isLogined ? <ContentPage /> : <UnloginedPage />}
    </div>
  );
}
