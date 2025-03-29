'use client';

import { useTranslations } from 'next-intl';
import React from 'react';
import AppIcon from '../../../../../public/images/components/AppIcon';
import Button from '../../common/Button';
import { useRouter } from '@/i18n/routing';
import { ERoutes } from '@/enums';

export default function UnloginedPage() {
  const t = useTranslations('home.unregistrated');
  const router = useRouter();
  return (
    <section className="flex flex-col gap-4 py-10 w-1/5 justify-center items-center">
      <div className="w-1/2">
        <AppIcon />
      </div>
      <p className="text-center">{t('not_registrated')}</p>
      <div className="flex gap-2">
        <Button
          text={t('registration')}
          handleClick={() => router.push(ERoutes.REGISTRATION)}
        />
        <Button
          text={t('sing_in')}
          handleClick={() => router.push(ERoutes.LOGIN)}
        />
      </div>
    </section>
  );
}
