'use client';

import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import { useTranslations } from 'next-intl';
import React from 'react';
import ChangeTransactionButton from './buttons/ChangeTransactionButton';
import DeleteTransactionButton from './buttons/DeleteTransactionButton';
import MoneyValue from '@/app/_components/common/MoneyValue';
import { useAppSelector } from '@/store/store';
import useTransactionOverview from '@/utils/hooks/useTransactionOverview';

export default function TransactionOverview() {
  const t = useTranslations('home.content.transactions.transaction');
  const { transaction } = useTransactionOverview();

  const categories = useAppSelector(
    (state) => state.dataActuality.userCategories
  );

  if (!transaction) return;

  const { categoryId, time, title, type, value, id }: TransactionProps =
    transaction;

  const category =
    categories.find((c) => c.id === Number(categoryId))?.title || '';

  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex gap-2 justify-between items-center">
        <h2 className="text-lg">{title}</h2>
        <div
          className="dark:text-gray_l text-gray_d  text-sm flex flex-col gap-1
        "
        >
          <p>{t('date')} :</p>
          <p>{new Date(time).toLocaleTimeString()}</p>
          <p>{new Date(time).toLocaleDateString()}</p>
        </div>
      </div>
      <div className="flex gap-2 justify-between items-end">
        <p>
          {t('category')} : {category}
        </p>
        <MoneyValue value={value} type={type} />
      </div>
      <div className=" flex justify-between w-full">
        <p></p>
        <div className="flex gap-2 justify-end  w-1/2">
          <ChangeTransactionButton />
          <DeleteTransactionButton id={id} />
        </div>
      </div>
    </div>
  );
}
