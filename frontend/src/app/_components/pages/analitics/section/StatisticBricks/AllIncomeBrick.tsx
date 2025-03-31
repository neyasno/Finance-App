'use client';

import { useTranslations } from 'next-intl';
import React from 'react';
import { CartesianGrid, Line, LineChart, XAxis, YAxis } from 'recharts';

const data = {
  day_income: 12,
  month_income: 21312,
  year_income: 121312,
  all_income: 231421312,
};

const data_day = [
  { name: '1', uv: 0, pv: 2400, amt: 2400 },
  { name: '2', uv: 0, pv: 2400, amt: 2400 },
  { name: '3', uv: 1400, pv: 2400, amt: 2400 },
  { name: '4', uv: 0, pv: 2400, amt: 2400 },
  { name: '5', uv: 2400, pv: 2400, amt: 2400 },
  { name: '6', uv: 0, pv: 2400, amt: 2400 },
  { name: '7', uv: 0, pv: 2, amt: 2400 },
  { name: '8', uv: 0, pv: 2400, amt: 2400 },
  { name: '9', uv: 0, pv: 2400, amt: 2400 },
  { name: '10', uv: 22400, pv: 2400, amt: 2400 },
  { name: '20', uv: 100, pv: 2400, amt: 2400 },
  { name: '22', uv: 0, pv: 2400, amt: 2400 },
  { name: '24', uv: 0, pv: 2400, amt: 2400 },
  { name: '25', uv: 0, pv: 2400, amt: 2400 },
  { name: '29', uv: 0, pv: 2400, amt: 2400 },
  { name: '30', uv: 0, pv: 2400, amt: 2400 },
];

export default function AllIncomeBrick() {
  const { all_income, day_income, month_income, year_income } = data;
  const t = useTranslations('analitics.content');
  return (
    <div className="flex flex-col gap-2">
      <h2 className="border-b-1 border-black dark:border-white py-5">
        {t('all_income')}
      </h2>
      <div className="flex gap-2">
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
          <p>
            {t('all')} : {all_income}
          </p>
        </div>
        <LineChart width={600} height={300} data={data_day}>
          <Line type="monotone" dataKey="uv" stroke="#8884d8" />
          <CartesianGrid stroke="#ccc" />
          <XAxis dataKey="name" />
          <YAxis />
        </LineChart>
      </div>
    </div>
  );
}
