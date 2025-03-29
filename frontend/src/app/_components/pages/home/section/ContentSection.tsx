import React from 'react';
import Budget from './Budget/Budget';
import Constraint from './Constraint/Constraint';
import CategoryBrick from './Category/CategoryBrick';

export default function ContentSection() {
  return (
    <section className="flex flex-col justify-center">
      <Budget />
      <Constraint />
      <CategoryBrick />
    </section>
  );
}
