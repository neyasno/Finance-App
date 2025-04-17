'use client';

import Button from '@/app/_components/common/buttons/Button';
import NumberInput from '@/app/_components/common/inputs/NumberInput';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React from 'react';
import ConstraintCategorySelector from './ConstraintCategorySelector';
import { useModal } from '@/utils/hooks/useModal';
import { ModalType } from '@/store/slices/modalSlice';

export default function CreateConstraintForm() {
  const t = useTranslations('home.content.transactions.transaction');

  const setModal = useModal();

  const [value, setValue] = React.useState(0);
  const [date, setDate] = React.useState(new Date().toISOString());
  const [category, setCategory] = React.useState('0');

  const changeConstraintReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    const newConstraint = {
      value,
      categoryId: parseInt(category),
      expirationTime: date + 'T00:00:00',
    };
    console.log(newConstraint);

    const res = await fetchApi(EApi.CONSTRAINTS, 'POST', newConstraint);
    console.log(res);

    setModal(ModalType.None);
  };

  return (
    <form action="" className="flex flex-col gap-2">
      <label htmlFor="value">{t('value')} : </label>
      <NumberInput
        handleChange={setValue}
        placeholder={t('value')}
        value={value}
      />

      <label htmlFor="category">{t('category')} : </label>
      <ConstraintCategorySelector setValue={setCategory} value={category} />
      <label htmlFor="date">{t('date')} : </label>
      <input
        type="date"
        lang="en"
        className="bg-transparent p-2 border-black border-1 dark:border-white"
        value={date}
        onChange={(e) => setDate(e.target.value)}
      />
      <Button text={t('create')} handleClick={changeConstraintReq} />
    </form>
  );
}
