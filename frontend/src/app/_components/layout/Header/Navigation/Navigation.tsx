import React from 'react';
import AnaliticsLink from './links/AnaliticsLink';
import HomeLink from './links/HomeLink';

export default function Navigation() {
  return (
    <div className="flex flex-nowrap gap-x-2 items-center">
      <HomeLink />
      <AnaliticsLink />
    </div>
  );
}
