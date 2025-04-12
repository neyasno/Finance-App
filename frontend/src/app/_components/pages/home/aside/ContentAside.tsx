import React from 'react';
import TransactionHistory from './TransactionHistory/TransactionHistory';
import AddTransactionButton from './AddTransactionButton/AddTransactionButton';

export default function ContentAside() {
  return (
    <aside className="flex flex-col gap-2 min-w-64">
      <TransactionHistory />
      <AddTransactionButton />
    </aside>
  );
}
