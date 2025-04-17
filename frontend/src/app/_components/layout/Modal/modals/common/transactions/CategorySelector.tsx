'use client';

import Select from '@/app/_components/common/inputs/Select';
import { useAppSelector } from '@/store/store';
import React, { useCallback } from 'react';

function CategorySelector({
  value,
  setValue,
}: {
  value: string;
  setValue: (value: string) => void;
}) {
  const memoizedSetValue = useCallback(setValue, [setValue]);

  const categories = useAppSelector(
    (state) => state.dataActuality.userCategories
  );
  const options = categories.map((category) => ({
    value: category.id.toString(),
    label: category.title,
  }));

  return <Select value={value} onChange={memoizedSetValue} options={options} />;
}

export default React.memo(CategorySelector);
