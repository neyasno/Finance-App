'use client';

import DeleteButton from '@/app/_components/common/buttons/DeleteButton';
import Loading from '@/app/_components/common/Loading';
import { EApi } from '@/enums';
import { setTransactionActuality } from '@/store/slices/dataActualitySlice';
import { ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';
import fetchApi from '@/utils/fetchApi';
import { useModal } from '@/utils/hooks/useModal';
import React, { useState } from 'react';

export default function DeleteTransactionButton({ id }: { id: number }) {
  const [isLoading, setLoading] = useState(false);
  const dispatch = useAppDispatch();
  const setModal = useModal();

  const deleteTransactionReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    try {
      setLoading(true);

      const res = await fetchApi(EApi.TRANSACTIONS + '/' + id, 'DELETE');
      console.log(res);

      dispatch(setTransactionActuality(false));

      setModal(ModalType.None);

      console.log('transaction deleted');
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
        <DeleteButton handleClick={deleteTransactionReq} />
      )}
    </>
  );
}
