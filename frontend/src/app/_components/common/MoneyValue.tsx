import React from 'react';

export default function MoneyValue({
  value,
  type,
  styles,
}: {
  value: number;
  styles?: string;
  type: 'income' | 'outcome';
}) {
  return (
    <div className={`flex gap-1 ${styles}`}>
      <p>{value}</p>
      <div
        className={`size-2 border-t-1 border-r-1 ${type === 'income' ? 'border-green' : 'border-red'}`}
      ></div>
    </div>
  );
}
