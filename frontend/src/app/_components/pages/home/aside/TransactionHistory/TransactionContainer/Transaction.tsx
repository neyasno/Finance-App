'use client';

import MoneyValue from '@/app/_components/common/MoneyValue';
import { useAppSelector } from '@/store/store';
import { useTransactionOverview } from '@/utils/hooks/useModal';
import React from 'react';

export type TransactionProps = {
  id: number;
  title: string;
  value: number;
  type: 'income' | 'outcome';
  time: string;
  categoryId: string;
};

export default function Transaction(transaction: TransactionProps) {
  const { categoryId, time, type, value } = transaction;
  const { setTransaction } = useTransactionOverview();
  const categories = useAppSelector(
    (state) => state.dataActuality.userCategories
  );
  const category =
    categories.find((c) => c.id === Number(categoryId))?.title || '';
  return (
    <div
      className="flex flex-col gap-1 p-2 border-1 text-sm hover:bg-white_d dark:hover:bg-black_l border-black dark:border-white"
      onClick={() => {
        setTransaction(transaction);
      }}
    >
      <div className="flex w-full justify-between items-center gap-5">
        <p className="overflow-hidden text-nowrap">
          {category.length > 7 ? `${category.slice(0, 7)}...` : category}
        </p>
        <p className="dark:text-gray_l text-gray_d ">
          {new Date(time).toLocaleDateString()}
        </p>
        <MoneyValue type={type} value={value} />
      </div>
    </div>
  );
}
