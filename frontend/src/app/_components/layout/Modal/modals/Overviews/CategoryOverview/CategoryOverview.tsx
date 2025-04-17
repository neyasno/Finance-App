import React from 'react';
import ChangeCategoryButton from './buttons/ChangeCategoryButton';
import DeleteCategoryButton from './buttons/DeleteCategoryButton';
import PeriodStatistic from '@/app/_components/pages/home/section/Budget/Statistic/PeriodStatistic';
import { useTranslations } from 'next-intl';
import useCategoryOverview from '@/utils/hooks/useCategoryOverview';

export default function CategoryOverview() {
  const t = useTranslations('home.content.statistic');
  const { category } = useCategoryOverview();
  const { title, day_income, day_outcome, id, month_income, month_outcome } =
    category;
  return (
    <div className="flex flex-col gap-5 w-full">
      <div className="flex gap-2 justify-between">
        <h2 className="text-lg">{title}</h2>
        <div className="flex gap-4">
          <PeriodStatistic
            title={t('day')}
            income={day_income}
            outcome={day_outcome}
          />
          <PeriodStatistic
            title={t('month')}
            income={month_income}
            outcome={month_outcome}
          />
        </div>
      </div>
      <div className=" flex justify-between w-full">
        <p></p>
        <div className="flex gap-2 justify-end  w-1/2">
          <ChangeCategoryButton id={id} />
          <DeleteCategoryButton id={id} />
        </div>
      </div>
    </div>
  );
}
