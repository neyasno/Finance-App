import { useTransactionOverview } from '@/utils/hooks/useModal';
import React from 'react';

export type TransactionProps = {
  id: string | number;
  title: string;
  value: number;
  type: 'income' | 'outcome';
  time: string;
  category: string;
};

export default function Transaction(transaction: TransactionProps) {
  const { setTransaction } = useTransactionOverview();
  return (
    <div
      className="flex flex-col gap-1 p-2 border-2 text-sm hover:bg-white_d dark:hover:bg-black_l"
      onClick={() => {
        setTransaction(transaction);
      }}
    >
      <div className="flex w-full justify-between items-center gap-5">
        <p>{transaction.category}</p>
        <p className="text-gray">{transaction.time}</p>
        <p
          className={` ${transaction.type === 'income' ? 'text-green' : 'text-red'}`}
        >
          {transaction.value}
        </p>
      </div>
    </div>
  );
}
