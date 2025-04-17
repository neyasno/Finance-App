'use client';

import Button from '@/app/_components/common/Button';
import NumberInput from '@/app/_components/common/NumberInput';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React from 'react';
import ConstraintCategorySelector from './ConstraintCategorySelector';
import { useModal } from '@/utils/hooks/useModal';
import { ModalType } from '@/store/slices/modalSlice';
import DeleteButton from '@/app/_components/common/DeleteButton';
import useConstraintOverview from '@/utils/hooks/useConstraintOverview';

export default function ChangeConstraintForm() {
  const t = useTranslations('home.content.transactions.transaction');

  const setModal = useModal();

  const { constraint } = useConstraintOverview();

  const [value, setValue] = React.useState(constraint.value);
  const [date, setDate] = React.useState(
    new Date(constraint.timeToExpire).toISOString()
  );
  const [category, setCategory] = React.useState('0');

  const updateConstraintReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    const updatedConstraint = {
      value,
      categoryId: parseInt(category),
      expirationTime: date + 'T00:00:00',
    };

    try {
      const res = await fetchApi(
        `${EApi.CONSTRAINTS}/${constraint.id}`,
        'PUT',
        updatedConstraint
      );
      console.log('Updated constraint:', res);

      setModal(ModalType.None);
    } catch (err) {
      console.error('Error updating constraint:', err);
    }
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

      <div className="flex gap-2">
        <Button text={t('change')} handleClick={updateConstraintReq} />
        <DeleteButton
          handleClick={async (e) => {
            e.preventDefault();

            await fetchApi(EApi.CONSTRAINTS + '/' + constraint.id, 'DELETE');

            setModal(ModalType.None);
          }}
        />
      </div>
    </form>
  );
}
