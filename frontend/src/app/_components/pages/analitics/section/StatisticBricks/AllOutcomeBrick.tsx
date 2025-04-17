'use client';

import Button from '@/app/_components/common/buttons/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import { CartesianGrid, Line, LineChart, XAxis, YAxis } from 'recharts';
import { TPeriod } from './AllIncomeBrick';

export default function AllOutcomeBrick() {
  const [period, setPeriod] = React.useState<TPeriod>('month');
  const [outcomeData, setOutcomeData] = React.useState<
    { name: string; outcome: number }[]
  >([]);

  useEffect(() => {
    const getOutcomeDataReq = async () => {
      try {
        const res: { name: string; outcome: number }[] = await fetchApi(
          EApi.ANALYTICS_OUTCOME + '?period=' + period,
          'GET'
        );
        setOutcomeData(res);
      } catch (err) {
        console.log(err);
      }
    };

    getOutcomeDataReq();
  }, [period]);

  const t = useTranslations('analitics.content');
  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('all_outcome')}
      </h2>
      <div className="flex gap-2">
        <div className="flex flex-col gap-2 w-1/4">
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
        <LineChart width={600} height={300} data={outcomeData}>
          <Line type="monotone" dataKey="outcome" stroke="#8884d8" />
          <CartesianGrid stroke="#ccc" />
          <XAxis dataKey="name" />
          <YAxis />
        </LineChart>
      </div>
    </div>
  );
}
