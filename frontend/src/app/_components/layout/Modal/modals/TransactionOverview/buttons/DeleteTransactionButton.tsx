import { useTranslations } from 'next-intl';
import React from 'react';

export default function DeleteTransactionButton({
  id,
}: {
  id: number | string;
}) {
  const t = useTranslations('home.content.transactions.transaction');

  const deleteTransactionReq = () => {
    console.log(id + 'deleted');
  };
  return (
    <button
      className="w-full py-2 border-2 transition 
                    px-1 md:px-3
                    text-center border-red
                    text-red
                    hover:bg-black hover:text-red 
                    dark:border-red dark:hover:bg-white dark:hover:text-red "
      onClick={deleteTransactionReq}
    >
      <b>{t('delete')}</b>
    </button>
  );
}
