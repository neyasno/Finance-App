import { RConstraint } from '@/app/_components/layout/Modal/modals/Overviews/ConstraintsOverview/ConstraintsOverwiev';
import { setModalTypeWithContent, ModalType } from '@/store/slices/modalSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { useCallback } from 'react';

const useConstraintOverview = () => {
  const dispatch = useAppDispatch();
  const constraint = useAppSelector(
    (state) => state.modal.content
  ) as RConstraint;

  const setConstraint = useCallback(
    (content: RConstraint) => {
      dispatch(
        setModalTypeWithContent({
          type: ModalType.ChangeConstraint,
          content,
        })
      );
    },
    [dispatch]
  );
  return { constraint, setConstraint };
};

export default useConstraintOverview;
