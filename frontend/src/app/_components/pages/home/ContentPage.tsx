import React from 'react';
import ContentSection from './section/ContentSection';
import ContentAside from './aside/ContentAside';

export default function ContentPage() {
  return (
    <div className="flex flex-col md:flex-row gap-4 pt-4">
      <div className="min-w-96">
        <ContentSection />
      </div>
      <ContentAside />
    </div>
  );
}
