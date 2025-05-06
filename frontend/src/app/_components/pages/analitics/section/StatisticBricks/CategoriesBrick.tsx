'use client';

import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect, useState, useMemo } from 'react';
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
import Button from '@/app/_components/common/buttons/Button';

type RawItem = {
  name: string;
  data: Record<string, number>;
};

type FlatItem = {
  name: string;
  [key: string]: string | number;
};

export default function CategoriesBrick() {
  const [period, setPeriod] = useState<TPeriod>('month');
  const [chartData, setChartData] = useState<FlatItem[]>([]);
  const [categoryKeys, setCategoryKeys] = useState<string[]>([]);
  const t = useTranslations('analitics.content');

  const getCategoriesDataReq = async () => {
    try {
      const raw: RawItem[] = await fetchApi(
        `${EApi.ANALYTICS_CATEGORIES}?period=${period}`,
        'GET'
      );

      const flat: FlatItem[] = raw.map(({ name, data }) => ({
        name,
        ...data,
      }));

      const categories = new Set<string>();
      flat.forEach((item) => {
        Object.keys(item).forEach((key) => {
          if (key !== 'name') categories.add(key);
        });
      });

      setChartData(flat);

      setCategoryKeys(Array.from(categories));
    } catch (err) {
      console.error('Ошибка при получении данных:', err);
    }
  };

  const getRandomColor = useMemo(() => {
    const colorMap = new Map<string, string>();
    return (key: string) => {
      if (!colorMap.has(key)) {
        const color = Math.floor(Math.random() * 0xffffff)
          .toString(16)
          .padStart(6, '0');
        colorMap.set(key, `#${color}`);
      }
      return colorMap.get(key)!;
    };
  }, []);

  useEffect(() => {
    getCategoriesDataReq();
  }, [period]);

  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b border-black dark:border-white py-5">
        {t('categories')}
      </h2>

      <div className="flex gap-4">
        <div className="flex flex-col gap-2 w-1/4">
          <Button text={t('day')} handleClick={() => setPeriod('day')} />
          <Button text={t('month')} handleClick={() => setPeriod('month')} />
          <Button text={t('year')} handleClick={() => setPeriod('year')} />
        </div>

        <BarChart
          width={700}
          height={300}
          data={chartData}
          margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis dataKey="name" />
          <YAxis />
          <Tooltip />
          <Legend align="left" verticalAlign="top" />
          {categoryKeys.map((key) => (
            <Bar key={key} dataKey={key} fill={getRandomColor(key)} />
          ))}
        </BarChart>
      </div>
    </div>
  );
}
