import React from 'react';
import Budget from './Budget/Budget';
import Constraint from './Constraint/Constraint';
import Categories from './Category/Categories';

export default function ContentSection() {
  return (
    <section className="flex flex-col gap-4">
      <Budget />
      <Constraint />
      <Categories />
    </section>
  );
}
