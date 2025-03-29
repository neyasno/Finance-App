import React from 'react';
import ContentSection from './section/ContentSection';
import ContentAside from './aside/ContentAside';

export default function ContentPage() {
  return (
    <div className="flex gap-4 pt-4">
      <ContentSection />
      <ContentAside />
    </div>
  );
}
