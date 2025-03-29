import React from 'react';
import CategoryBrick from './CategoryBrick';
import CreateCategoryButton from './CreateCategoryButton';
import { useTranslations } from 'next-intl';

export default function Categories() {
  const t = useTranslations('home.content.categories');
  return (
    <div className="flex flex-col gap-4">
      <div className="flex gap-4 text-sm">
        <p className="text-gray_d dark:text-gray_l">{t('sort_by') + ':'}</p>
        <button className="flex gap-1 border-b-1 border-black dark:border-white">
          <p>{t('income')}</p>
          <div className={'size-2 border-t-1 border-r-1 border-green'}></div>
        </button>
        <button className="flex gap-1">
          <p>{t('outcome')}</p>
          <div className={'size-2 border-t-1 border-r-1 border-red'}></div>
        </button>
      </div>
      <div className="flex flex-wrap gap-2">
        <div className="w-5/12">
          <CategoryBrick title="Food" type="income" value={24300} />
        </div>
        <div className="w-5/12">
          <CategoryBrick title="Food" type="income" value={24300} />
        </div>
        <div className="w-5/12">
          <CategoryBrick title="Food" type="income" value={24300} />
        </div>
        <div className="w-1/3">
          <CreateCategoryButton />
        </div>
      </div>
    </div>
  );
}
