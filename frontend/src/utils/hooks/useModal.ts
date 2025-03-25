import { ModalType, setModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';

export const useModal = () => {
  const dispatch = useAppDispatch();
  return (type: ModalType) => {
    dispatch(setModalType(type));
  };
};
