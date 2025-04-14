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

    const convertToCSV = (data: object[]) => {
      if (!data.length) return '';

      const headers = Object.keys(data[0]);
      const csvRows = [headers.join(',')];

      for (const row of data) {
        const values = headers.map((header) => {
          const cellValue = (row as Record<string, unknown>)[header] ?? '';
          const escaped = String(cellValue).replace(/"/g, '""');
          return `"${escaped}"`;
        });
        csvRows.push(values.join(','));
      }

      return csvRows.join('\n');
    };

    const res = await fetchApi(EApi.TRANSACTIONS + '?page=0&size=200', 'GET');
    console.log(res.content);
  };

  return <Button text={t('export_csv')} handleClick={getCSVReportReq} />;
}
