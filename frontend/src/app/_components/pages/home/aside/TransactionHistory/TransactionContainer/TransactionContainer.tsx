'use client';

import React from 'react';
import Transaction, { TransactionProps } from './Transaction';
import Loading from '@/app/_components/common/Loading';
import { useTranslations } from 'next-intl';

export default function TransactionContainer({
  isLoading,
  transactions,
}: {
  isLoading: boolean;
  transactions: TransactionProps[];
}) {
  const t = useTranslations('home.content.transactions');

  return (
    <ul className="flex flex-col gap-2">
      {isLoading ? (
        <Loading />
      ) : (
        <>
          {transactions.length > 0 ? (
            transactions.map((t) => (
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
            ))
          ) : (
            <p className="text-center w-full py-3 italic text-gray_d dark:text-gray_l">
              {t('no_transactions')}...
            </p>
          )}
        </>
      )}
    </ul>
  );
}
