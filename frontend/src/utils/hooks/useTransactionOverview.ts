import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import { setModalTypeWithContent, ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { useCallback } from 'react';

const useTransactionOverview = () => {
  const dispatch = useAppDispatch();
  const transaction: TransactionProps = useAppSelector(
    (state) => state.modal.content
  ) as TransactionProps;

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

export default useTransactionOverview;
