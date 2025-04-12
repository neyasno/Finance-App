'use client';

import Button from '@/app/_components/common/Button';
import NumberInput from '@/app/_components/common/NumberInput';
import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import ConstraintCategorySelector from './ConstraintCategorySelector';
import { useConstraintOverview, useModal } from '@/utils/hooks/useModal';
import { ModalType } from '@/store/slices/modalSlice';
import DeleteButton from '@/app/_components/common/DeleteButton';

type ChangeConstraintFormProps = {
  constraintId: string;
};

export default function ChangeConstraintForm({
  constraintId,
}: ChangeConstraintFormProps) {
  const t = useTranslations('home.content.transactions.transaction');

  const setModal = useModal();

  const { constraint } = useConstraintOverview();

  const [value, setValue] = React.useState(constraint.value);
  const [date, setDate] = React.useState(
    new Date(constraint.timeToExpire).toISOString()
  );
  const [category, setCategory] = React.useState('0');
  const [isLoading, setIsLoading] = React.useState(true);

  useEffect(() => {
    const changeConstraintReq = async () => {
      try {
        setIsLoading(true);

        const res = await fetchApi(
          EApi.CONSTRAINTS + '/' + constraintId,
          'GET'
        );

        setValue(res.value || 0);

        setDate(
          res.expirationTime
            ? res.expirationTime.split('T')[0]
            : new Date().toISOString()
        );

        setCategory(res.categoryId?.toString() || '0');

        setIsLoading(false);
      } catch (err) {
        console.error('Error fetching constraint:', err);

        setIsLoading(false);
      }
    };

    changeConstraintReq();
  }, [constraintId]);

  const updateConstraintReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    const updatedConstraint = {
      value,
      expirationTime: date + 'T00:00:00',
    };

    try {
      const res = await fetchApi(
        `${EApi.CONSTRAINTS}/${constraintId}`,
        'PUT',
        updatedConstraint
      );
      console.log('Updated constraint:', res);

      setModal(ModalType.None);
    } catch (err) {
      console.error('Error updating constraint:', err);
    }
  };

  if (isLoading) {
    return <div>Loading...</div>;
  }

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
          handleClick={(e) => {
            e.preventDefault();

            setModal(ModalType.None);
          }}
        />
      </div>
    </form>
  );
}
