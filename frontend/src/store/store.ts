import { Action, configureStore, ThunkAction } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';

import userReducer from './slices/userSlice';
import modalReducer from './slices/modalSlice';
import taskBoardsReducer from './slices/taskBoardsSlice';
import dragReducer from './slices/dragSlice';
import { thunk } from 'redux-thunk';

export const store = configureStore({
  reducer: {
    user: userReducer,
    modal: modalReducer,
    taskBoards: taskBoardsReducer,
    drag: dragReducer,
  },
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppDispatch: () => AppDispatch = useDispatch;
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
export type AppThunk = ThunkAction<void, RootState, unknown, Action<string>>;
