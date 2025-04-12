'use client';

import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import {
  Bar,
  BarChart,
  Brush,
  CartesianGrid,
  Legend,
  ReferenceLine,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts';

export type TAnaliticUnit = {
  name: string;
  income: number;
  outcome: number;
};

export default function GeneralBrick() {
  const t = useTranslations('analitics.content');

  const [generalData, setGeneralData] = React.useState<TAnaliticUnit[]>([]);
  const [period, setPeriod] = React.useState<'day' | 'month' | 'year'>('month');

  const getGeneralDataReq = async () => {
    try {
      const res: TAnaliticUnit[] = await fetchApi(
        EApi.ANALYTICS_GENERAL + '?period=' + period,
        'GET'
      );
      console.log(res);

      setGeneralData(res);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getGeneralDataReq();
  });

  return (
    <div className="flex flex-col">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('general')}
      </h2>
      <div className="flex flex-row gap-2 pt-2">
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
        <BarChart
          width={600}
          height={400}
          data={generalData}
          margin={{
            top: 5,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend
            verticalAlign="top"
            align="left"
            width={260}
            wrapperStyle={{ lineHeight: '40px' }}
          />
          <ReferenceLine y={0} stroke="#000" />
          <Brush dataKey="name" height={30} stroke="#8884d8" />
          <Bar dataKey="income" fill="rgba(40,170,40)" />
          <Bar dataKey="outcome" fill="rgba(170,50,50)" />
        </BarChart>
      </div>
    </div>
  );
}
