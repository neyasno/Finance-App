import React, { useEffect, useState } from 'react';
import CategoryBrick from './CategoryBrick';
import CreateCategoryButton from './CreateCategoryButton';
import { useTranslations } from 'next-intl';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import Loading from '@/app/_components/common/Loading';
import { useAppDispatch, useAppSelector } from '@/store/store';
import {
  setCategoriesActuality,
  setUserCategories,
} from '@/store/slices/dataActualitySlice';

export type RCategory = {
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
  const [sorting, setSorting] = useState<'income' | 'outcome'>('income');

  const { isCategoriesActual, isTransactionsActual } = useAppSelector(
    (state) => state.dataActuality
  );
  const dispatch = useAppDispatch();

  useEffect(() => {
    const getCategoriesReq = async () => {
      try {
        setLoading(true);

        const res: RCategory[] = await fetchApi(EApi.CATEGORIES, 'GET');
        console.log(res);

        res.sort((a, b) => b.monthIncome - a.monthIncome);

        setCategories(res);
      } catch (err) {
        console.log(err);
      } finally {
        dispatch(setUserCategories(categories));

        dispatch(setCategoriesActuality(true));

        setLoading(false);
      }
    };

    getCategoriesReq();
  }, [isCategoriesActual, isTransactionsActual, dispatch]);

  useEffect(() => {
    const sortedCategories = [...categories].sort((a, b) =>
      sorting === 'income'
        ? b.monthIncome - a.monthIncome
        : b.monthOutcome - a.monthOutcome
    );

    setCategories(sortedCategories);
  }, [sorting]);

  return (
    <div className="flex flex-col gap-4">
      <div className="flex gap-4 text-sm">
        <p className="text-gray_d dark:text-gray_l">{t('sort_by') + ':'}</p>
        <button
          className={`flex gap-1 border-black dark:border-white  ${sorting === 'income' && 'border-b-1'}`}
          onClick={() => setSorting('income')}
        >
          <p>{t('income')}</p>
          <div className={'size-2 border-t-1 border-r-1 border-green'}></div>
        </button>
        <button
          className={`flex gap-1 border-black dark:border-white  ${sorting === 'outcome' && 'border-b-1'}`}
          onClick={() => setSorting('outcome')}
        >
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
