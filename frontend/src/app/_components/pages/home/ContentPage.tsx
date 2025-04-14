import React from 'react';
import ContentSection from './section/ContentSection';
import ContentAside from './aside/ContentAside';

export default function ContentPage() {
  return (
    <div className="flex flex-col items-center md:items-start md:flex-row gap-4 pt-4 justify-center">
      <div className="sm:min-w-96">
        <ContentSection />
      </div>
      <div className="flex justify-center w-full">
        <ContentAside />
      </div>
    </div>
  );
}
