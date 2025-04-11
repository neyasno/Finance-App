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

  const [categoriesData, setCategoriesData] = React.useState<[]>([]);
  const t = useTranslations('analitics.content');

  const getCategoriesDataReq = async () => {
    try {
      const res = await fetchApi(EApi.ANALYTICS_CATEGORIES, 'GET');
      console.log(res);
    } catch (err) {
      console.log(err);
    }
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
        <div className="flex flex-col gap-2">
          <div className="flex flex-col gap-1 px-2">
            <p>
              {t('most_income_category')} : {32131}
            </p>
            <p>
              {t('most_outcome_category')} : {23131}
            </p>
            <p>
              {t('less_income_category')} : {231}
            </p>
            <p>
              {t('less_outcome_category')} : {3213}
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
          <Legend />
          <Bar dataKey="transport" stackId="a" fill="#8884d8" />
          <Bar dataKey="work" stackId="a" fill="#82ca9d" />
          <Bar dataKey="food" fill="#ffc658" />
        </BarChart>
      </div>
    </div>
  );
}
