'use client';

import Button from '@/app/_components/common/Button';
import TextInput from '@/app/_components/common/TextInput';
import { useTranslations } from 'next-intl';
import React, { useState } from 'react';

export default function CreateCategoryForm() {
  const t = useTranslations('home.content.categories');
  const [title, setTitle] = useState('');

  const createCategoryReq = () => {
    console.log('da');
  };
  return (
    <form action="submit" className="flex flex-col gap-2 ">
      <TextInput
        value={title}
        handleChange={setTitle}
        placeholder={t('category_name')}
      />
      <Button text={t('create')} handleClick={createCategoryReq} />
    </form>
  );
}
