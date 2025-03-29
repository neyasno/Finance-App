import Button from '@/app/_components/common/Button';
import ContsraintValue from '@/app/_components/common/ContsraintValue';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function Constraint() {
  const t = useTranslations('home.content.constraint');
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
        <Button text={t('change')} handleClick={() => {}} />
      </div>
    </div>
  );
}
