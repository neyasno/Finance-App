import { useTranslations } from 'next-intl';
import React from 'react';

export default function DeleteButton({
  handleClick,
}: {
  handleClick: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
}) {
  const t = useTranslations('home.content.transactions.transaction');
  return (
    <button
      className="w-full py-2 border-2 transition 
                  px-1 md:px-3
                  text-center border-red
                  text-red
                  hover:bg-black hover:text-red 
                  dark:border-red dark:hover:bg-white dark:hover:text-red "
      onClick={handleClick}
    >
      <b>{t('delete')}</b>
    </button>
  );
}
