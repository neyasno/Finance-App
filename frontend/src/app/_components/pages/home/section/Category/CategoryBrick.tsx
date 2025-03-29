import MoneyValue from '@/app/_components/common/MoneyValue';
import { getDifference } from '@/utils/calculator';
import { useCategoryOverview } from '@/utils/hooks/useModal';
import React from 'react';

export type CategoryBrickProps = {
  id: string | number;
  title: string;
  day_income: number;
  day_outcome: number;
  month_income: number;
  month_outcome: number;
  year_income: number;
  year_outcome: number;
};

export default function CategoryBrick(category: CategoryBrickProps) {
  const { title, month_income, month_outcome } = category;
  const { setCategory } = useCategoryOverview();

  const { difference, type } = getDifference(month_income, month_outcome);
  return (
    <div
      className="flex flex-col p-3 rounded-md gap-1 border-1 border-black dark:border-white
                    hover:bg-white_d dark:hover:bg-black_l"
      onClick={() => setCategory(category)}
    >
      <div className="flex">
        <p>{title}</p>
        <div></div>
      </div>
      <div className="flex gap-2 justify-between">
        <div></div>
        <MoneyValue value={difference} type={type} />
      </div>
    </div>
  );
}
