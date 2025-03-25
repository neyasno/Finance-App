'use client';

import React, { useEffect } from 'react';

import { useAppSelector } from '@/store/store';
import ThemeButton from './Button-Bar/ThemeButton';

import AppTitle from './Navigation/AppTitle';
import LocaleButton from './Button-Bar/LocaleButton';
import LogoutButton from './Button-Bar/LogoutButton';

export default function Header() {
  const isLogined = useAppSelector((state) => state.user.isLogined);

  useEffect(() => {}, [isLogined]);

  return (
    <div className="dark:bg-black w-full flex flex-nowrap justify-between px-6 py-1">
      <div className="flex flex-nowrap gap-x-2">
        <AppTitle />
      </div>
      <div className="flex gap-2">
        <LocaleButton />
        <ThemeButton />
        {true && <LogoutButton />}
      </div>
    </div>
  );
}
