'use client';

import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import PeriodStatistic from './Statistic/PeriodStatistic';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import calculateBudget from '@/utils/budgetCalculator';
import { useAppSelector } from '@/store/store';

export default function Budget() {
  const t = useTranslations('home.content.statistic');
  const [budget, setBudget] = React.useState<number>(0);
  const [dayIncome, setDayIncome] = React.useState<number>(0);
  const [dayOutcome, setDayOutcome] = React.useState<number>(0);
  const [monthIncome, setMonthIncome] = React.useState<number>(0);
  const [monthOutcome, setMonthOutcome] = React.useState<number>(0);
  const [yearIncome, setYearIncome] = React.useState<number>(0);
  const [yearOutcome, setYearOutcome] = React.useState<number>(0);

  const { isTransactionsActual } = useAppSelector(
    (state) => state.dataActuality
  );

  useEffect(() => {
    console.log('BUDGET EFFECT!!!');

    const fetchBudgets = async () => {
      try {
        const [dayRes, monthRes, yearRes] = await Promise.all([
          fetchApi(`${EApi.ANALYTICS_GENERAL}?period=day`, 'GET'),
          fetchApi(`${EApi.ANALYTICS_GENERAL}?period=month`, 'GET'),
          fetchApi(`${EApi.ANALYTICS_GENERAL}?period=year`, 'GET'),
        ]);

        const dayStats = calculateBudget(dayRes);
        const monthStats = calculateBudget(monthRes);
        const yearStats = calculateBudget(yearRes);

        console.log(dayRes);

        console.log(dayStats);

        setDayIncome(dayStats.all_income);

        setDayOutcome(dayStats.all_outcome);

        setBudget(monthStats.budget);

        setMonthIncome(monthStats.all_income);

        setMonthOutcome(monthStats.all_outcome);

        setYearIncome(yearStats.all_income);

        setYearOutcome(yearStats.all_outcome);
      } catch (err) {
        console.error(err);
      }
    };

    fetchBudgets();

    console.log('BUDGET EFFECT END!!!');
  }, [isTransactionsActual]);

  return (
    <div className="flex gap-5 border-b-1 justify-between border-black dark:border-white py-4">
      <h1 className="text-4xl ">{budget}</h1>
      <div className="flex gap-1">
        <PeriodStatistic
          title={t('day')}
          income={dayIncome}
          outcome={dayOutcome}
        />
        <PeriodStatistic
          title={t('month')}
          income={monthIncome}
          outcome={monthOutcome}
        />
        <div className="hidden sm:flex">
          <PeriodStatistic
            title={t('year')}
            income={yearIncome}
            outcome={yearOutcome}
          />
        </div>
      </div>
    </div>
  );
}
