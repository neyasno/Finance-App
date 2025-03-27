import { ERoutes } from '@/enums';
import { Link } from '@/i18n/routing';
import React from 'react';

export default function AnaliticsLink() {
  return (
    <Link href={ERoutes.ANALITICS}>
      <div className="p-2 hover:underline">
        <p>Anatilics</p>
      </div>
    </Link>
  );
}
