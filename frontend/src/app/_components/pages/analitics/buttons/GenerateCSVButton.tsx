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

    const link = document.createElement('a');
    link.href = 'http://localhost:3000/api/export-transactions';

    link.setAttribute('download', 'transactions.csv');

    link.setAttribute('target', '_blank');

    document.body.appendChild(link);

    link.click();

    document.body.removeChild(link);
  };

  return <Button text={t('export_csv')} handleClick={getCSVReportReq} />;
}
