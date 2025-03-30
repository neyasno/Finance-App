import { useCategoryOverview } from '@/utils/hooks/useModal';
import React from 'react';
import ChangeCategoryButton from './buttons/ChangeCategoryButton';
import DeleteCategoryButton from './buttons/DeleteCategoryButton';
import PeriodStatistic from '@/app/_components/pages/home/section/Budget/Statistic/PeriodStatistic';

export default function CategoryOverview() {
  const { category } = useCategoryOverview();
  const { title } = category;
  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex gap-2 justify-between">
        <h2 className="text-lg">{title}</h2>
        <div className="flex gap-4">
          <PeriodStatistic title="day" income={31240} outcome={31241} />
          <PeriodStatistic title="day" income={31240} outcome={31241} />
          <PeriodStatistic title="day" income={31240} outcome={31241} />
        </div>
      </div>
      <div className=" flex justify-between w-full">
        <p></p>
        <div className="flex gap-2 justify-end  w-1/2">
          <ChangeCategoryButton id={1} />
          <DeleteCategoryButton id={1} />
        </div>
      </div>
    </div>
  );
}
