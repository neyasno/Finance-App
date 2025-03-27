import { setIsLogined } from '@/store/slices/userSlice';
import { useAppDispatch, useAppSelector } from '@/store/store';
import { useCallback } from 'react';

export const useLogin = () => {
  const isLogined = useAppSelector((state) => state.user.isLogined);
  const dispatch = useAppDispatch();

  const setLogined = useCallback(
    (status: boolean) => {
      dispatch(setIsLogined(status));
    },
    [dispatch]
  );

  return { isLogined, setLogined };
};
