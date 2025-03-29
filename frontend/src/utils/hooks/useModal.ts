import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import {
  ModalType,
  setModalType,
  setModalTypeWithContent,
} from '@/store/slices/modalSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { useCallback } from 'react';

export const useModal = () => {
  const dispatch = useAppDispatch();
  return useCallback(
    (type: ModalType) => {
      dispatch(setModalType(type));
    },
    [dispatch]
  );
};

export const useTransactionOverview = () => {
  const dispatch = useAppDispatch();
  const transaction = useAppSelector((state) => state.modal.content);

  const setTransaction = useCallback(
    (content: TransactionProps) => {
      dispatch(
        setModalTypeWithContent({
          type: ModalType.TransactionOverview,
          content,
        })
      );
    },
    [dispatch]
  );
  return { transaction, setTransaction };
};
