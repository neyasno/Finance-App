'use client';

import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import {
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar,
} from 'recharts';
import { TPeriod } from './AllIncomeBrick';
import Button from '@/app/_components/common/Button';

export default function CategoriesBrick() {
  const [period, setPeriod] = React.useState<TPeriod>('month');

  const [categoriesData] = React.useState<[]>([]);
  const t = useTranslations('analitics.content');

  const getCategoriesDataReq = async () => {
    try {
      const res = await fetchApi(
        EApi.ANALYTICS_CATEGORIES + `?period=${period}`,
        'GET'
      );
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  const getRandomColor = () => {
    const color = Math.floor(0x1000000 * Math.random()).toString(16);
    return '#' + ('000000' + color).slice(-6);
  };

  useEffect(() => {
    getCategoriesDataReq();
  });

  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('categories')}
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
        <BarChart
          width={650}
          height={300}
          data={categoriesData}
          margin={{
            top: 20,
            right: 30,
            left: 20,
            bottom: 5,
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend align="left" verticalAlign="top" width={230} />
          {categoriesData.map((item, index) => (
            <Bar key={index} dataKey={item} fill={getRandomColor()} />
          ))}
        </BarChart>
      </div>
    </div>
  );
}
