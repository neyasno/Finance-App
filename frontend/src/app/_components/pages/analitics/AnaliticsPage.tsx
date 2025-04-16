import React from 'react';
import AnaliticsContent from './section/AnaliticsContent';
import GenerateCSVButton from './buttons/GenerateCSVButton';
import GeneratePDFButton from './buttons/GeneratePDFButton';

export default function AnaliticsPage() {
  return (
    <div className="flex flex-col gap-2 pt-10">
      <div className="flex justify-center sm:justify-end print:hidden">
        <div className="flex gap-2 w-1/3">
          <div className="hidden sm:flex">
            <GeneratePDFButton />
          </div>
          <div className="flex">
            <GenerateCSVButton />
          </div>
        </div>
      </div>
      <AnaliticsContent />
    </div>
  );
}
