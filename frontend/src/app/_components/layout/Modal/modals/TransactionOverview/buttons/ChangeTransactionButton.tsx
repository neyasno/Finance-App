import Button from '@/app/_components/common/Button';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function ChangeTransactionButton({
  id,
}: {
  id: string | number;
}) {
  const t = useTranslations('home.content.transactions.transaction');

  const changeTransactionReq = () => {
    console.log(id + ' changed');
  };
  return <Button text={t('change')} handleClick={changeTransactionReq} />;
}
