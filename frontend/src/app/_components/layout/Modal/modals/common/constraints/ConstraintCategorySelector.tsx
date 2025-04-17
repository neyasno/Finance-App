'use client';

import Select from '@/app/_components/common/inputs/Select';
import { useAppSelector } from '@/store/store';
import { useTranslations } from 'next-intl';

export default function ConstraintCategorySelector({
  value,
  setValue,
}: {
  value: string;
  setValue: (value: string) => void;
}) {
  const t = useTranslations('home.content.transactions');

  const categories = useAppSelector(
    (state) => state.dataActuality.userCategories
  );
  const options = categories.map((category) => ({
    value: category.id.toString(),
    label: category.title,
  }));

  options.push({
    value: '0',
    label: t('all'),
  });

  console.log('options:::');

  console.log(options);

  return <Select value={value} onChange={setValue} options={options} />;
}
