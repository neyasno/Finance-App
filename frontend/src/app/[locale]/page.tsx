'use client';

import { useLogin } from '@/utils/hooks/useLogin';
import ContentPage from '../_components/pages/home/ContentPage';
import UnloginedPage from '../_components/pages/home/UnloginedPage';

export default function Home() {
  const { isLogined } = useLogin();
  return (
    <div className="flex justify-center items-center px-2 pt-1">
      {isLogined ? <ContentPage /> : <UnloginedPage />}
    </div>
  );
}
