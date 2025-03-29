import React from 'react';
import Transaction, { TransactionProps } from './Transaction';

const transactions: TransactionProps[] = [
  {
    id: 1,
    category: 'food',
    time: '20.03.2025',
    title: 'Lunch Burger',
    type: 'income',
    value: 12000,
  },
  {
    id: 1,
    category: 'food',
    time: '20.03.2025',
    title: 'Lunch Burger',
    type: 'outcome',
    value: 2000,
  },
  {
    id: 1,
    category: 'food',
    time: '20.03.2025',
    title: 'Lunch Burger',
    type: 'outcome',
    value: 2000,
  },
  {
    id: 1,
    category: 'food',
    time: '20.03.2025',
    title: 'Lunch Burger',
    type: 'outcome',
    value: 2000,
  },
];

export default function TransactionContainer() {
  return (
    <ul className="flex flex-col gap-2">
      {transactions.map((t, index) => (
        <li key={index}>
          <Transaction
            id={t.id}
            category={t.category}
            time={t.time}
            title={t.title}
            type={t.type}
            value={t.value}
          />
        </li>
      ))}
    </ul>
  );
}
