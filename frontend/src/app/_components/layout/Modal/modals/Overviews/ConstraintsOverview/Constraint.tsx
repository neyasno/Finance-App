'use client';

import React from 'react';
import { RConstraint } from './ConstraintsOverwiev';
import { useTranslations } from 'next-intl';
import ContsraintValue from '@/app/_components/common/ContsraintValue';
import { useConstraintOverview } from '@/utils/hooks/useModal';

export default function Constraint({
  constraintData,
}: {
  constraintData: RConstraint;
}) {
  const { value, available, timeToExpire } = constraintData;
  const t = useTranslations('home.content.constraint');

  const { setConstraint } = useConstraintOverview();

  const openChangeConstraintModal = () => {
    setConstraint(constraintData);
  };

  return (
    <div
      className="flex flex-col p-3 w-full gap-3 border-1 border-black dark:border-white hover:bg-white_d dark:hover:bg-black_l"
      onClick={openChangeConstraintModal}
    >
      <div className="flex justify-between gap-2">
        <p className="text-lg">{'Food'}</p>
        <p className="text-gray_d dark:text-gray_l">
          {timeToExpire.split('T')[0]}
        </p>
      </div>
      <div className="flex justify-between gap-1">
        <p>
          {t('value')} : {value}
        </p>
        <p className="text-xl">
          <ContsraintValue value={available} />
        </p>
      </div>
    </div>
  );
}
