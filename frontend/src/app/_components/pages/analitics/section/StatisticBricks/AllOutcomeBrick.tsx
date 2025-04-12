'use client';

import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import { CartesianGrid, Line, LineChart, XAxis, YAxis } from 'recharts';
import { TPeriod } from './AllIncomeBrick';

const data = {
  day_outcome: 15,
  month_outcome: 21315,
  year_outcome: 121315,
  all_outcome: 231421315,
};

export default function AllOutcomeBrick() {
  const [period, setPeriod] = React.useState<TPeriod>('month');
  const { day_outcome, month_outcome, year_outcome } = data;
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
        <div className="flex flex-col gap-2">
          <div className="flex flex-col gap-1 px-2">
            <p>
              {t('day')} : {day_outcome}
            </p>
            <p>
              {t('month')} : {month_outcome}
            </p>
            <p>
              {t('year')} : {year_outcome}
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
