import { ERoutes } from '@/enums';
import { Link } from '@/i18n/routing';
import React from 'react';

export default function HomeLink() {
  return (
    <Link href={ERoutes.DEFAULT}>
      <div className="p-2 hover:underline">
        <p>Home</p>
      </div>
    </Link>
  );
}
