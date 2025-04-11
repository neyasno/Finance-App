import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';
import { CategoryBrickProps } from '@/app/_components/pages/home/section/Category/CategoryBrick';
import { createSlice } from '@reduxjs/toolkit';

export enum ModalType {
  None,
  ChangeConstraint,
  CreateConstraint,
  CreateTransaction,
  ChangeTransaction,
  DeleteSuggestion,
  ExitSuggestion,
  TransactionOverview,
  CreateCategory,
  CategoryOverview,
  ChangeCategory,
  ConstraintsOverview,
}

type ModalSlice = {
  type: ModalType;
  content?: TransactionProps | CategoryBrickProps;
};

const initialState: ModalSlice = {
  type: ModalType.None,
};

const modalSlice = createSlice({
  name: 'modal',
  initialState: initialState,
  reducers: {
    setModalType: (state, action) => {
      console.log(action.payload);

      state.type = action.payload;
    },
    setModalTypeWithContent: (state, action) => {
      console.log(action.payload);

      state.type = action.payload.type;

      state.content = action.payload.content;
    },
  },
});

export const { setModalType, setModalTypeWithContent } = modalSlice.actions;
export default modalSlice.reducer;
