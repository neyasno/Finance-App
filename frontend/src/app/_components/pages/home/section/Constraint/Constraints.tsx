import Button from '@/app/_components/common/Button';
import ContsraintValue from '@/app/_components/common/ContsraintValue';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function Constraints() {
  const t = useTranslations('home.content.constraint');
  const setModal = useModal();
  return (
    <div className="flex justify-between items-center">
      <div className="flex flex-col gap-1">
        <div className="flex gap-1">
          <p>{t('constraint') + ':'} </p>
          <ContsraintValue value={31411} />
        </div>
        <div className="flex gap-1">
          <p>{t('available') + ':'} </p>
          <ContsraintValue value={31411} />
        </div>
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
