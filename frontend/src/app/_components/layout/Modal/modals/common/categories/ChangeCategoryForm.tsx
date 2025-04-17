import Button from '@/app/_components/common/buttons/Button';
import Loading from '@/app/_components/common/Loading';
import TextInput from '@/app/_components/common/inputs/TextInput';
import { EApi } from '@/enums';
import { setCategoriesActuality } from '@/store/slices/dataActualitySlice';
import { ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import fetchApi from '@/utils/fetchApi';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React, { useState } from 'react';

export default function ChangeCategoryForm() {
  const t = useTranslations('home.content.categories');
  const id = useAppSelector((state) => state.dataActuality.currentCategory);

  const dispatch = useAppDispatch();
  const setModal = useModal();

  const [title, setTitle] = useState('');
  const [isLoading, setLoading] = useState(false);

  const createCategoryReq = async (
    e: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) => {
    e.preventDefault();

    try {
      setLoading(true);

      await fetchApi(EApi.CATEGORIES + '/' + id, 'PUT', { title });

      dispatch(setCategoriesActuality(false));

      setModal(ModalType.None);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };
  return (
    <form className="flex flex-col gap-2 ">
      <TextInput
        value={title}
        handleChange={setTitle}
        placeholder={t('category_name')}
      />
      {isLoading ? (
        <Loading />
      ) : (
        <Button text={t('create')} handleClick={createCategoryReq} />
      )}
    </form>
  );
}
