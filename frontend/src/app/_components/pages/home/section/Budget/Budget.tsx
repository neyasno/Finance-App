import { useTranslations } from 'next-intl';
import React from 'react';
import PeriodStatistic from './Statistic/PeriodStatistic';

export default function Budget() {
  const t = useTranslations('home.content.statistic');
  return (
    <div className="flex gap-5 border-b-1 border-black dark:border-white py-4">
      <h1 className="text-4xl ">214000</h1>
      <PeriodStatistic title={t('day')} income={1210} outcome={21411} />
      <PeriodStatistic title={t('month')} income={1210} outcome={21411} />
      <PeriodStatistic title={t('year')} income={1210} outcome={21411} />
    </div>
  );
}
