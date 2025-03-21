'use client';
import { useLocale } from 'next-intl';
import { useRouter } from 'next/navigation';
import React from 'react';

export default function LanguageButton() {
  const router = useRouter();
  const currentLocale = useLocale();

  const switchLanguage = () => {
    const newLocale = currentLocale === 'en' ? 'ru' : 'en';
    router.push(`/${newLocale}`);
  };

  return (
    <button
      className="text-center justify-center items-center p-3 
                       rounded-full hover:underline"
      onClick={switchLanguage}
    >
      {currentLocale === 'en' ? 'ru' : 'en'}
    </button>
  );
}
