'use client';

import React, { useEffect } from 'react';
import { useLogin } from '@/utils/hooks/useLogin';
import Navigation from './Navigation/Navigation';
import ButtonBar from './Button-Bar/ButtonBar';
import AppTitle from './AppTitle';

export default function Header() {
  const { isLogined } = useLogin();

  useEffect(() => {}, [isLogined]);

  return (
    <div className="dark:bg-black w-full flex flex-nowrap justify-between px-6 py-1">
      <div className="flex gap-10 justify-center items-center">
        <AppTitle />
        <div className="pt-1">
          <Navigation />
        </div>
      </div>
      <ButtonBar />
    </div>
  );
}
