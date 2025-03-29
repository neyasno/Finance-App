import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import { CategoryBrickProps } from '@/app/_components/pages/home/section/Category/CategoryBrick';
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

export const useCategoryOverview = () => {
  const dispatch = useAppDispatch();
  const category = useAppSelector(
    (state) => state.modal.content
  ) as CategoryBrickProps;

  const setCategory = useCallback(
    (content: CategoryBrickProps) => {
      dispatch(
        setModalTypeWithContent({
          type: ModalType.CategoryOverview,
          content,
        })
      );
    },
    [dispatch]
  );
  return { category, setCategory };
};
