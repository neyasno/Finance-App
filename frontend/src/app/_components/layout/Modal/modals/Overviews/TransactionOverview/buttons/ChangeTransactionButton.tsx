import Button from '@/app/_components/common/Button';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function ChangeTransactionButton() {
  const t = useTranslations('home.content.transactions.transaction');
  const setModal = useModal();
  const changeTransactionReq = () => {
    setModal(ModalType.ChangeTransaction);
  };
  return <Button text={t('change')} handleClick={changeTransactionReq} />;
}
