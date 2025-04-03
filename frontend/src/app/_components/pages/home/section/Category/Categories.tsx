import React, { useEffect, useState } from 'react';
import CategoryBrick from './CategoryBrick';
import CreateCategoryButton from './CreateCategoryButton';
import { useTranslations } from 'next-intl';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import Loading from '@/app/_components/common/Loading';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { setCategoriesActuality } from '@/store/slices/dataActualitySlice';

type RCategory = {
  id: number;
  title: string;
  dayIncome: number;
  dayOutcome: number;
  monthOutcome: number;
  monthIncome: number;
  transactions: [];
};

export default function Categories() {
  const t = useTranslations('home.content.categories');
  const [categories, setCategories] = useState<RCategory[]>([]);
  const [isLoading, setLoading] = useState(false);

  const isCategoriesActual = useAppSelector(
    (state) => state.dataActuality.isCategoriesActual
  );
  const dispatch = useAppDispatch();

  useEffect(() => {
    const getCategoriesReq = async () => {
      try {
        setLoading(true);

        const res: RCategory[] = await fetchApi(EApi.CATEGORIES, 'GET');
        console.log(res);

        setCategories(res);
      } catch (err) {
        console.log(err);
      } finally {
        dispatch(setCategoriesActuality(true));

        setLoading(false);
      }
    };

    getCategoriesReq();
  }, [isCategoriesActual, dispatch]);

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
      <div className="flex flex-wrap gap-2 max-w-lg">
        {isLoading ? (
          <Loading />
        ) : (
          categories.map((c, index) => (
            <div className="w-5/12" key={index}>
              <CategoryBrick
                id={c.id}
                title={c.title}
                day_income={c.dayIncome}
                day_outcome={c.dayOutcome}
                month_income={c.monthIncome}
                month_outcome={c.monthOutcome}
              />
            </div>
          ))
        )}
        <div className="w-1/3 h-16">
          <CreateCategoryButton />
        </div>
      </div>
    </div>
  );
}
