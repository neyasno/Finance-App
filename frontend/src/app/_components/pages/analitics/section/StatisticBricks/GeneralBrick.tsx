'use client';

import { useTranslations } from 'next-intl';
import React from 'react';
import {
  Bar,
  BarChart,
  Brush,
  CartesianGrid,
  Cell,
  Legend,
  Pie,
  PieChart,
  ReferenceLine,
  Tooltip,
  XAxis,
  YAxis,
} from 'recharts';

const data = [
  { name: 'income', value: 4000 },
  { name: 'outcome', value: 800 },
];

const data2 = [
  { name: '1', income: 1300, outcome: -200 },
  { name: '2', income: 100, outcome: -200 },
  { name: '3', income: 500, outcome: -200 },
  { name: '4', income: 100, outcome: -200 },
  { name: '5', income: 100, outcome: -200 },
  { name: '6', income: 1, outcome: -200 },
  { name: '7', income: 0, outcome: -200 },
  { name: '8', income: 300, outcome: -200 },
  { name: '9', income: 300, outcome: -200 },
  { name: '10', income: 300, outcome: -200 },
  { name: '11', income: 300, outcome: -200 },
  { name: '12', income: 300, outcome: -200 },
  { name: '13', income: 300, outcome: -200 },
  { name: '14', income: 300, outcome: -200 },
  { name: '15', income: 300, outcome: -200 },
  { name: '16', income: 300, outcome: -200 },
  { name: '17', income: 300, outcome: -200 },
  { name: '18', income: 300, outcome: -200 },
  { name: '19', income: 300, outcome: -200 },
  { name: '20', income: 300, outcome: -200 },
  { name: '21', income: 300, outcome: -200 },
  { name: '22', income: 300, outcome: -200 },
  { name: '23', income: 300, outcome: -200 },
  { name: '24', income: 300, outcome: -200 },
  { name: '25', income: 300, outcome: -200 },
  { name: '26', income: 300, outcome: -200 },
  { name: '27', income: 300, outcome: -200 },
  { name: '28', income: 300, outcome: -200 },
  { name: '29', income: 300, outcome: -200 },
  { name: '30', income: 300, outcome: -200 },
  { name: '31', income: 300, outcome: -200 },
];

export default function GeneralBrick() {
  const t = useTranslations('analitics.content');
  return (
    <div className="flex flex-col">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('general')}
      </h2>
      <div className="flex flex-row gap-2 pt-2">
        <div className="flex flex-col gap-2">
          <p>
            {t('budget')} : {32143142}
          </p>
          <p>
            {t('income')} : {32143142}
          </p>
          <p>
            {t('outcome')} : {32143142}
          </p>
          <PieChart width={200} height={200}>
            <Pie
              data={data}
              cx="50%"
              cy="50%"
              outerRadius={80}
              fill="#8884d8"
              dataKey="value"
            >
              <Cell fill="rgba(40,170,40)" />
              <Cell fill="rgba(170,50,50)" />
            </Pie>
          </PieChart>
        </div>
        <BarChart
          width={600}
          height={400}
          data={data2}
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
          <Legend verticalAlign="top" wrapperStyle={{ lineHeight: '40px' }} />
          <ReferenceLine y={0} stroke="#000" />
          <Brush dataKey="name" height={30} stroke="#8884d8" />
          <Bar dataKey="income" fill="rgba(40,170,40)" />
          <Bar dataKey="outcome" fill="rgba(170,50,50)" />
        </BarChart>
      </div>
    </div>
  );
}
