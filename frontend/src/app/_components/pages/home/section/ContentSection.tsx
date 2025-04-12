import React from 'react';
import Budget from './Budget/Budget';
import Constraints from './Constraint/Constraints';
import Categories from './Category/Categories';

export default function ContentSection() {
  return (
    <section className="flex flex-col gap-4">
      <Budget />
      <Constraints />
      <Categories />
    </section>
  );
}
