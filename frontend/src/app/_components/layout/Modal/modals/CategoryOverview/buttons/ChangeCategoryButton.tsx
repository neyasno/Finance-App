import Button from '@/app/_components/common/Button';
import { setCurrentCategory } from '@/store/slices/dataActualitySlice';
import { ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function ChangeCategoryButton({ id }: { id: string | number }) {
  const t = useTranslations('home.content.transactions.transaction');
  const setModal = useModal();
  const dispatch = useAppDispatch();
  const changeTransactionReq = () => {
    dispatch(setCurrentCategory(id));

    setModal(ModalType.ChangeCategory);
  };
  return <Button text={t('change')} handleClick={changeTransactionReq} />;
}
