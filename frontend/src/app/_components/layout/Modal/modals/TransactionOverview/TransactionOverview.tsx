import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import { useTransactionOverview } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';
import ChangeTransactionButton from './buttons/ChangeTransactionButton';
import DeleteTransactionButton from './buttons/DeleteTransactionButton';

export default function TransactionOverview() {
  const t = useTranslations('home.content.transactions.transaction');
  const { transaction } = useTransactionOverview();

  if (!transaction) return;

  const { category, time, title, type, value }: TransactionProps = transaction;

  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex gap-2 justify-between items-center">
        <h2 className="text-lg">{title}</h2>
        <p
          className="dark:text-gray_l text-gray_d  text-sm
        "
        >
          {t('date')}: {time}
        </p>
      </div>
      <div className="flex gap-2 justify-between items-end">
        <p>
          {t('category')} : {category}
        </p>
        <p
          className={`text-xl ${type === 'income' ? 'text-green' : 'text-red'}`}
        >
          {value}
        </p>
      </div>
      <div className=" flex justify-between w-full">
        <p></p>
        <div className="flex gap-2 justify-end  w-1/2">
          <ChangeTransactionButton id={1} />
          <DeleteTransactionButton id={1} />
        </div>
      </div>
    </div>
  );
}
