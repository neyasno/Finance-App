import Button from '@/app/_components/common/Button';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function AddTransactionButton() {
  const t = useTranslations('home.content.transactions');
  const setModal = useModal();
  return (
    <Button
      text={t('create')}
      handleClick={() => setModal(ModalType.CreateTransaction)}
    />
  );
}
