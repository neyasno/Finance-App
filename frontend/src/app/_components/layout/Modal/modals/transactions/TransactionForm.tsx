import NumberInput from '@/app/_components/common/NumberInput';
import TextInput from '@/app/_components/common/TextInput';
import React from 'react';
import TypeSelector from './TypeSelector';
import CategorySelector from './CategorySelector';
import Button from '@/app/_components/common/Button';
import { useTranslations } from 'next-intl';

type TransactionFormProps = {
  transactionReq: (e: React.MouseEvent<HTMLElement, MouseEvent>) => void;
  setTitle: React.Dispatch<React.SetStateAction<string>>;
  setValue: React.Dispatch<React.SetStateAction<number>>;
  title: string;
  value: number;
  type: 'income' | 'outcome';
  setType: React.Dispatch<React.SetStateAction<'income' | 'outcome'>>;
  category: string;
  setCategory: React.Dispatch<React.SetStateAction<string>>;
};

export default function TransactionForm({
  setTitle,
  setType,
  setValue,
  title,
  transactionReq,
  type,
  value,
  category,
  setCategory,
}: TransactionFormProps) {
  const t = useTranslations('home.content.transactions.transaction');
  return (
    <form action="" className="flex flex-col gap-3">
      <div className="flex flex-col gap-2">
        <label htmlFor="title">{t('title')} :</label>
        <TextInput
          value={title}
          placeholder={t('title')}
          handleChange={setTitle}
        />

        <label htmlFor="value">{t('value')} :</label>
        <NumberInput
          value={value}
          placeholder={t('value')}
          handleChange={setValue}
        />
      </div>

      <div className="flex flex-col gap-2">
        <label htmlFor="type">{t('type')} : </label>
        <TypeSelector value={type} setValue={setType} />

        <label htmlFor="category">{t('category')} : </label>
        <CategorySelector value={category} setValue={setCategory} />
      </div>

      <Button handleClick={transactionReq} text={t('create')} />
    </form>
  );
}
