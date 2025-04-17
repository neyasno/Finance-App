import { CategoryBrickProps } from '@/app/_components/pages/home/section/Category/CategoryBrick';
import { setModalTypeWithContent, ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { useCallback } from 'react';

const useCategoryOverview = () => {
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

export default useCategoryOverview;
