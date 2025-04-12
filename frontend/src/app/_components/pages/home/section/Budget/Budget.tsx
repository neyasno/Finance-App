'use client';

import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import PeriodStatistic from './Statistic/PeriodStatistic';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import { TAnaliticUnit } from '../../../analitics/section/StatisticBricks/GeneralBrick';
import calculateBudget from '@/utils/hooks/budgetCalculator';
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

  const { isTransactionsActual, currentTransaction } = useAppSelector(
    (state) => state.dataActuality
  );

  console.log(currentTransaction);

  useEffect(() => {
    const getBudget = async () => {
      try {
        const res: TAnaliticUnit[] = await fetchApi(
          EApi.ANALYTICS_GENERAL + '?period=month',
          'GET'
        );

        const budgetStatistic = calculateBudget(res);
        setBudget(budgetStatistic.budget);

        console.log(res);
      } catch (err) {
        console.log(err);
      }
    };

    const getDayBudget = async () => {
      try {
        const res: TAnaliticUnit[] = await fetchApi(
          EApi.ANALYTICS_GENERAL + '?period=day',
          'GET'
        );

        const budgetStatistic = calculateBudget(res);
        setDayIncome(budgetStatistic.all_income);

        setDayOutcome(budgetStatistic.all_outcome);

        console.log(res);
      } catch (err) {
        console.log(err);
      }
    };

    const getMonthBudget = async () => {
      try {
        const res: TAnaliticUnit[] = await fetchApi(
          EApi.ANALYTICS_GENERAL + '?period=month',
          'GET'
        );

        const budgetStatistic = calculateBudget(res);
        setMonthIncome(budgetStatistic.all_income);

        setMonthOutcome(budgetStatistic.all_outcome);

        console.log(res);
      } catch (err) {
        console.log(err);
      }
    };

    const getYearBudget = async () => {
      try {
        const res: TAnaliticUnit[] = await fetchApi(
          EApi.ANALYTICS_GENERAL + '?period=year',
          'GET'
        );

        const budgetStatistic = calculateBudget(res);
        setYearIncome(budgetStatistic.all_income);

        setYearOutcome(budgetStatistic.all_outcome);

        console.log(res);
      } catch (err) {
        console.log(err);
      }
    };

    getBudget();

    getDayBudget();

    getMonthBudget();

    getYearBudget();
  }, [isTransactionsActual]);

  return (
    <div className="flex gap-5 border-b-1 border-black dark:border-white py-4">
      <h1 className="text-4xl ">{budget}</h1>
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
      <PeriodStatistic
        title={t('year')}
        income={yearIncome}
        outcome={yearOutcome}
      />
    </div>
  );
}
