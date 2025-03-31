'use client';

import Button from '@/app/_components/common/Button';
import { useTranslations } from 'next-intl';
import React from 'react';

export default function GenerateCSVButton() {
  const t = useTranslations('analitics.content');
  return <Button text={t('export_csv')} handleClick={() => console.log(1)} />;
}
