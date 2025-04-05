'use client';

import React, { useEffect, useState } from 'react';
import Transaction, { TransactionProps } from './Transaction';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import Loading from '@/app/_components/common/Loading';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { setTransactionActuality } from '@/store/slices/dataActualitySlice';

export default function TransactionContainer() {
  const dispatch = useAppDispatch();
  const { isTransactionsActual } = useAppSelector(
    (state) => state.dataActuality
  );
  const [transactions, setTransactions] = React.useState<TransactionProps[]>(
    []
  );
  const [isLoading, setLoading] = useState(false);

  const fetchTransactions = async () => {
    try {
      setLoading(true);

      const res = await fetchApi(EApi.TRANSACTIONS + '?page=0', 'GET');

      console.log(res.content);

      setTransactions(res.content);

      dispatch(setTransactionActuality(true));
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTransactions();
  }, [isTransactionsActual]);

  return (
    <ul className="flex flex-col gap-2">
      {isLoading ? (
        <Loading />
      ) : (
        <>
          {transactions.map((t, index) => (
            <li key={index}>
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
