'use client';

import React from 'react';
import LocaleButton from './buttons/LocaleButton';
import ThemeButton from './buttons/ThemeButton';
import LogoutButton from './buttons/LogoutButton';
import { useLogin } from '@/utils/hooks/useLogin';

export default function ButtonBar() {
  const { isLogined } = useLogin();
  return (
    <div className="flex sm:gap-2">
      <LocaleButton />
      <ThemeButton />
      {isLogined && <LogoutButton />}
    </div>
  );
}
