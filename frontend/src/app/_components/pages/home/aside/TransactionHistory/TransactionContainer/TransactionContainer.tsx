'use client';

import React from 'react';
import Transaction, { TransactionProps } from './Transaction';
import Loading from '@/app/_components/common/Loading';

export default function TransactionContainer({
  isLoading,
  transactions,
}: {
  isLoading: boolean;
  transactions: TransactionProps[];
}) {
  return (
    <ul className="flex flex-col gap-2">
      {isLoading ? (
        <Loading />
      ) : (
        <>
          {transactions.map((t) => (
            <li key={t.id}>
              <Transaction
                categoryId={t.categoryId}
                id={t.id}
                time={t.time}
                title={t.title}
                type={t.type}
                value={t.value}
              />
            </li>
          ))}
        </>
      )}
    </ul>
  );
}
