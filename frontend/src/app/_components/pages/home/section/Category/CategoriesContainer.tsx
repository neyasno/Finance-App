import React from 'react';
import { RCategory } from './Categories';
import CategoryBrick from './CategoryBrick';

export default function CategoriesContainer({
  categories,
}: {
  categories: RCategory[];
}) {
  return (
    <>
      {categories.map((c, index) => (
        <div className="w-5/12" key={index}>
          <CategoryBrick
            id={c.id}
            title={c.title}
            day_income={c.dayIncome}
            day_outcome={c.dayOutcome}
            month_income={c.monthIncome}
            month_outcome={c.monthOutcome}
          />
        </div>
      ))}
    </>
  );
}
