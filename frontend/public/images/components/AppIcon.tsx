'use client';

import { useTheme } from 'next-themes';
import React from 'react';

export default function AppIcon() {
  const { theme } = useTheme();

  return (
    <div className="w-full h-full">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 100 100"
        className="w-full h-full"
      >
        <g fill="none" stroke={theme === 'dark' ? '#fff' : '#000'}>
          <path
            opacity="NaN"
            d="M525.038 335.154c14 8.615 30.616-2.77 25.539 9.846s-39.539-18.462-25.539-9.846z"
          />
          <path
            opacity="NaN"
            d="M521.5 225c24 43 10 98 7 99s-1 31-43 53-16 9-64 21-63-28-63-32-4-56 30-58 72 11 88-18 69-3 14-39-166-87-85-33 123 76 37 67-158 35-132-42-30-57 38-84 33-43 90-20 60-2 78 42-19 1 5 44z"
          />
          <path
            stroke-width="3"
            d="M49.985 77.213c11.218-6.567 31.014-5.004 16.149-7.836-7.432-1.416-4.342-5.236.497-8.719 2.42-1.742 4.631-4.037 6.029-7.909.698-1.936 2.357-8.245-.938-18.12-1.646-4.937-6.141-10.988-9.886-14.587-3.743-3.6-1.728-26.033 5.354 6.033"
            opacity="NaN"
          />
          <path
            stroke-width="3"
            d="M50.09 77.191c-12.344 6.083-15.743 4.652-18.784 5.457s-9.481.447-8.408-3.489 1.789-13.238 13.596-17.531 4.83-22.183 1.43-.179"
            opacity="NaN"
          />
        </g>
      </svg>
    </div>
  );
}
