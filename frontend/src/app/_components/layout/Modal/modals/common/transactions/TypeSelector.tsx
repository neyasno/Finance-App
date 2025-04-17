import React from 'react';
import Select from '@/app/_components/common/inputs/Select';

const typeOptions = [
  { value: 'income', label: 'Income' },
  { value: 'outcome', label: 'Outcome' },
];

export default function TypeSelector({
  value,
  setValue,
}: {
  value: 'income' | 'outcome';
  setValue: (value: 'income' | 'outcome') => void;
}) {
  return (
    <Select
      value={value}
      onChange={(value) => setValue(value as 'income' | 'outcome')}
      options={typeOptions}
    />
  );
}
