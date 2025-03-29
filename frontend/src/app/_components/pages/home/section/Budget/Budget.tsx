import MoneyValue from '@/app/_components/common/MoneyValue';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function Budget() {
  const t = useTranslations('home.content.statistic');
  return (
    <div className="flex gap-5 border-b-1 border-black dark:border-white py-4">
      <h1 className="text-4xl ">214000</h1>
      <div className="flex flex-col text-sm dark:text-gray_l text-gray_d">
        <p>{t('day')}</p>
        <div className="flex flex-col px-2">
          <MoneyValue value={1000} type="income" />
          <MoneyValue value={1000} type="outcome" />
        </div>
      </div>
      <div className="flex flex-col text-sm dark:text-gray_l text-gray_d">
        <p>{t('month')}</p>
        <div className="flex flex-col px-2">
          <MoneyValue value={1000} type="income" />
          <MoneyValue value={1000} type="outcome" />
        </div>
      </div>
      <div className="flex flex-col text-sm dark:text-gray_l text-gray_d">
        <p>{t('year')}</p>
        <div className="flex flex-col px-2">
          <MoneyValue value={1000} type="income" />
          <MoneyValue value={1000} type="outcome" />
        </div>
      </div>
    </div>
  );
}
