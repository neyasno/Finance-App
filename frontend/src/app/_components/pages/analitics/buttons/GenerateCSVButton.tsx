'use client';

import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function GenerateCSVButton() {
  const t = useTranslations('analitics.content');

  const getCSVReportReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    const res = await fetchApi(EApi.TRANSACTIONS + '?page=0&size=200', 'GET');
    console.log(res.content);
  };

  return <Button text={t('export_csv')} handleClick={getCSVReportReq} />;
}
