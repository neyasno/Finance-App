import MoneyValue from '@/app/_components/common/MoneyValue';
import React from 'react';

export type PeriodStatisticProps = {
  title: string;
  income: number;
  outcome: number;
};

export default function PeriodStatistic({
  income,
  outcome,
  title,
}: PeriodStatisticProps) {
  return (
    <div className="flex flex-col text-sm dark:text-gray_l text-gray_d">
      <p>{title}</p>
      <div className="flex flex-col px-2">
        <MoneyValue value={income} type="income" />
        <MoneyValue value={outcome} type="outcome" />
      </div>
    </div>
  );
}
