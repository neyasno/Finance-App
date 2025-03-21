'use client';

import React, { useEffect } from 'react';

import { useAppSelector } from '@/store/store';
import { useTranslations } from 'next-intl';
import ThemeButton from './Button-Bar/ThemeButton';

import AppTitle from './Navigation/AppTitle';

export default function Header() {
  const user = useAppSelector((state) => state.user);

  useEffect(() => {}, [user.isLogined]);

  return (
    <div className="dark:bg-black w-full flex flex-nowrap justify-between px-6 py-1">
      <div className="flex flex-nowrap gap-x-2">
        <AppTitle />
      </div>
      <div className="flex gap-4">
        <ThemeButton />
      </div>
    </div>
  );
}
