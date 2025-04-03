'use client';

import { useTranslations } from 'next-intl';
import React from 'react';
import {
  BarChart,
  CartesianGrid,
  XAxis,
  YAxis,
  Tooltip,
  Legend,
  Bar,
} from 'recharts';

const data = [
  {
    name: '1',
    food: -4000,
    transport: 2400,
    work: 2400,
  },
  {
    name: '2',
    food: 3000,
    transport: 1398,
    work: 2210,
  },
  {
    name: '3',
    food: 2000,
    transport: 9800,
    work: 2290,
  },
  {
    name: '4',
    food: 2780,
    transport: 3908,
    work: 2000,
  },
  {
    name: '5',
    food: 1890,
    transport: 4800,
    work: 2181,
  },
  {
    name: '6',
    food: 2390,
    transport: 3800,
    work: 2500,
  },
  {
    name: '7',
    food: 3490,
    transport: 4300,
    work: 2100,
  },
];

export default function CategoriesBrick() {
  const t = useTranslations('analitics.content');
  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('categories')}
      </h2>
      <div className="flex gap-2">
        <div className="flex flex-col gap-2">
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
        <BarChart
          width={650}
          height={300}
          data={data}
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
