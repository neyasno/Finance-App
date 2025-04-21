'use client';

import Button from '@/app/_components/common/buttons/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React from 'react';
import { TransactionProps } from '../../home/aside/TransactionHistory/TransactionContainer/Transaction';

export default function GenerateCSVButton() {
  const t = useTranslations('analitics.content');

  const getCSVReportReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    const res = await fetchApi(`${EApi.TRANSACTIONS}?page=0&size=200`, 'GET');

    const transactions: TransactionProps[] = res.content;

    console.log(transactions);

    const response = await fetch('/api/export-transactions', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(transactions),
    });

    if (!response.ok) {
      console.error('Ошибка при загрузке CSV', response.status);

      return;
    }

    const blob = await response.blob();
    const downloadUrl = URL.createObjectURL(blob);

    window.open(downloadUrl, '_blank');
  };

  return <Button text={t('export_csv')} handleClick={getCSVReportReq} />;
}
