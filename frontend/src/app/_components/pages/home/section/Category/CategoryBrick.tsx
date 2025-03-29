import MoneyValue from '@/app/_components/common/MoneyValue';
import React from 'react';

export default function CategoryBrick({
  value,
  type,
  title,
}: {
  value: number;
  type: 'income' | 'outcome';
  title: string;
}) {
  return (
    <div className="flex flex-col p-3 rounded-md gap-1 border-1 border-black dark:border-white">
      <div className="flex">
        <p>{title}</p>
        <div></div>
      </div>
      <div className="flex gap-2 justify-between">
        <div></div>
        <MoneyValue value={value} type={type} />
      </div>
    </div>
  );
}
