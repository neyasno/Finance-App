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

    const res = await fetchApi(EApi.TRANSACTIONS + '?page=0&size=200', 'GET');
    console.log(res.content);

    const header = ['id', 'title', 'value', 'type', 'time', 'categoryId'];
    const csvRows = [];
    csvRows.push(header.join(','));

    res.content.forEach((transaction: TransactionProps) => {
      const values = [
        transaction.id,
        `"${transaction.title}"`,
        transaction.value,
        transaction.type,
        `"${transaction.time}"`,
        transaction.categoryId,
      ];
      csvRows.push(values.join(','));
    });

    const csvData = csvRows.join('\n');

    const blob = new Blob([csvData], { type: 'text/csv;charset=utf-8;' });
    const url = window.URL.createObjectURL(blob);

    const link = document.createElement('a');
    link.href = url;

    link.download = 'transactions.csv';

    document.body.appendChild(link);

    link.click();

    document.body.removeChild(link);

    window.URL.revokeObjectURL(url);
  };

  return <Button text={t('export_csv')} handleClick={getCSVReportReq} />;
}
