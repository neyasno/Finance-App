import React from 'react';
import LocaleButton from './buttons/LocaleButton';
import ThemeButton from './buttons/ThemeButton';
import LogoutButton from './buttons/LogoutButton';

export default function ButtonBar() {
  return (
    <div className="flex gap-2">
      <LocaleButton />
      <ThemeButton />
      {true && <LogoutButton />}
    </div>
  );
}
