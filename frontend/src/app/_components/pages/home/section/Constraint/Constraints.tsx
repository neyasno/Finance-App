import Button from '@/app/_components/common/buttons/Button';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function Constraints() {
  const t = useTranslations('home.content.constraint');
  const setModal = useModal();
  return (
    <div className="flex justify-between items-center">
      <div className="flex gap-1">
        <h2>{t('constraints')}</h2>
        <div className={'size-2 border-1 border-green'}></div>
      </div>
      <div className="">
        {' '}
        <Button
          text={t('change')}
          handleClick={() => {
            setModal(ModalType.ConstraintsOverview);
          }}
        />
      </div>
    </div>
  );
}
