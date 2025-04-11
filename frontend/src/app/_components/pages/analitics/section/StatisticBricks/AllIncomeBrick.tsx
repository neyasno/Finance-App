'use client';

import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import { CartesianGrid, Line, LineChart, XAxis, YAxis } from 'recharts';

export type TPeriod = 'day' | 'month' | 'year';

const data = {
  day_income: 12,
  month_income: 21312,
  year_income: 121312,
  all_income: 231421312,
};

export default function AllIncomeBrick() {
  const [period, setPeriod] = React.useState<TPeriod>('month');

  const { day_income, month_income, year_income } = data;
  const [incomeData, setIncomeData] = React.useState<
    { name: string; income: number }[]
  >([]);

  const getIncomeDataReq = async () => {
    try {
      const res: { name: string; income: number }[] = await fetchApi(
        EApi.ANALYTICS_INCOME + '?period=' + period,
        'GET'
      );
      setIncomeData(res);

      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getIncomeDataReq();
  }, [period]);

  const t = useTranslations('analitics.content');
  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('all_income')}
      </h2>
      <div className="flex gap-2">
        <div className="flex flex-col gap-2">
          <div className="flex flex-col gap-1 px-2">
            <p>
              {t('day')} : {day_income}
            </p>
            <p>
              {t('month')} : {month_income}
            </p>
            <p>
              {t('year')} : {year_income}
            </p>
          </div>
          <div className="flex flex-col">
            <Button
              text={t('day')}
              handleClick={() => {
                setPeriod('day');
              }}
            />
            <Button
              text={t('month')}
              handleClick={() => {
                setPeriod('month');
              }}
            />
            <Button
              text={t('year')}
              handleClick={() => {
                setPeriod('year');
              }}
            />
          </div>
        </div>
        <LineChart width={600} height={300} data={incomeData}>
          <Line type="monotone" dataKey="uv" stroke="#8884d8" />
          <CartesianGrid stroke="#ccc" />
          <XAxis dataKey="name" />
          <YAxis />
        </LineChart>
      </div>
    </div>
  );
}
