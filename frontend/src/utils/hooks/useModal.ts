import { ModalType, setModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';
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
