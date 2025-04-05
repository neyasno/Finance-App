'use client';

import Select from '@/app/_components/common/Select';
import { useAppSelector } from '@/store/store';
import React from 'react';

export default function CategorySelector({
  value,
  setValue,
}: {
  value: string;
  setValue: (value: string) => void;
}) {
  const categories = useAppSelector(
    (state) => state.dataActuality.userCategories
  );
  const options = categories.map((category) => ({
    value: category.id.toString(),
    label: category.title,
  }));
  console.log('options:::');

  console.log(options);

  return <Select value={value} onChange={setValue} options={options} />;
}
