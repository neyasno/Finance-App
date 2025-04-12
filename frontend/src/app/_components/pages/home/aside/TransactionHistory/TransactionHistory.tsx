'use client';

import { useTranslations } from 'next-intl';
import React, { useEffect, useState } from 'react';
import TransactionContainer from './TransactionContainer/TransactionContainer';
import { setTransactionActuality } from '@/store/slices/dataActualitySlice';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import { TransactionProps } from './TransactionContainer/Transaction';
import { useAppDispatch, useAppSelector } from '@/store/store';

export default function TransactionHistory() {
  const t = useTranslations('home.content.transactions');

  const [page, setPage] = React.useState(0);
  const [totalPages, setTotalPages] = React.useState(0);

  const dispatch = useAppDispatch();
  const { isTransactionsActual } = useAppSelector(
    (state) => state.dataActuality
  );
  const [transactions, setTransactions] = React.useState<TransactionProps[]>(
    []
  );
  const [isLoading, setLoading] = useState(false);

  const handlePageChange = (page: number) => {
    if (page <= totalPages - 1 && page >= 0) setPage(page);
  };

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        setLoading(true);

        const res = await fetchApi(
          EApi.TRANSACTIONS + `?page=${page}&size=5`,
          'GET'
        );
        console.log(res);

        setTotalPages(res.totalPages);

        console.log(res.content);

        setTransactions(res.content);

        dispatch(setTransactionActuality(true));
      } catch (err) {
        console.log(err);
      } finally {
        setLoading(false);
      }
    };

    fetchTransactions();
  }, [isTransactionsActual, page, dispatch]);

  return (
    <div className="flex flex-col gap-2 w-full">
      <div className="flex justify-between">
        <p>{t('history')}</p>
        <div className={`flex gap-2 ${totalPages <= 1 && 'hidden'}`}>
          <button
            hidden={page === 0}
            onClick={() => {
              handlePageChange(page - 1);
            }}
          >
            {'<'}
          </button>
          <div>{page}</div>
          <button
            hidden={page === totalPages - 1}
            onClick={() => {
              handlePageChange(page + 1);
            }}
          >
            {'>'}
          </button>
        </div>
      </div>
      <TransactionContainer isLoading={isLoading} transactions={transactions} />
    </div>
  );
}
