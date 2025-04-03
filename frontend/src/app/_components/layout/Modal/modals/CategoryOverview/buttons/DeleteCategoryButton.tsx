'ues client';

import DeleteButton from '@/app/_components/common/DeleteButton';
import Loading from '@/app/_components/common/Loading';
import { EApi } from '@/enums';
import { setCategoriesActuality } from '@/store/slices/dataActualitySlice';
import { ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';
import fetchApi from '@/utils/fetchApi';
import { useModal } from '@/utils/hooks/useModal';
import React, { useState } from 'react';

export default function DeleteCategoryButton({ id }: { id: number }) {
  const [isLoading, setLoading] = useState(false);
  const dispatch = useAppDispatch();
  const setModal = useModal();

  const deleteCategoryReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    try {
      setLoading(true);

      await fetchApi(EApi.CATEGORIES + '/' + id, 'DELETE');

      dispatch(setCategoriesActuality(false));

      setModal(ModalType.None);
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {isLoading ? (
        <Loading />
      ) : (
        <DeleteButton handleClick={deleteCategoryReq} />
      )}
    </>
  );
}
