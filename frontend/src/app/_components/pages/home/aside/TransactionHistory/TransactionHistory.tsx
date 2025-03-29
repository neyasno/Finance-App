import { useTranslations } from 'next-intl';
import React from 'react';
import TransactionContainer from './TransactionContainer/TransactionContainer';

export default function TransactionHistory() {
  const t = useTranslations('home.content.transactions');
  return (
    <div className="flex flex-col gap-2">
      <p>{t('history')}</p>
      <TransactionContainer />
    </div>
  );
}
