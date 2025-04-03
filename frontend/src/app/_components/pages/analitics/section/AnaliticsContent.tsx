import React from 'react';
import AllIncomeBrick from './StatisticBricks/AllIncomeBrick';
import AllOutcomeBrick from './StatisticBricks/AllOutcomeBrick';
import GeneralBrick from './StatisticBricks/GeneralBrick';
import CategoriesBrick from './StatisticBricks/CategoriesBrick';

export default function AnaliticsContent() {
  return (
    <section className="flex flex-col gap-4">
      <GeneralBrick />
      <AllIncomeBrick />
      <AllOutcomeBrick />
      <CategoriesBrick />
    </section>
  );
}
