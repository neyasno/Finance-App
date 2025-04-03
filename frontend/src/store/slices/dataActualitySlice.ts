import { createSlice } from '@reduxjs/toolkit';

type DataActuality = {
  isBudgetActual: boolean;
  isCategoriesActual: boolean;
  isTransactionsActual: boolean;
  isConstraintsActual: boolean;
  currentTransaction: number;
  currentConstraint: number;
  currentCategory: number;
};

const initialState: DataActuality = {
  isBudgetActual: false,
  isCategoriesActual: false,
  isTransactionsActual: false,
  isConstraintsActual: false,
  currentTransaction: NaN,
  currentConstraint: NaN,
  currentCategory: NaN,
};

const dataActualitySlice = createSlice({
  name: 'dataActuality',
  initialState: initialState,
  reducers: {
    setBudgetActuality: (state, action) => {
      state.isBudgetActual = action.payload;
    },
    setCategoriesActuality: (state, action) => {
      state.isCategoriesActual = action.payload;
    },
    setTransactionActuality: (state, action) => {
      state.isTransactionsActual = action.payload;
    },
    setConstrainstActuality: (state, action) => {
      state.isConstraintsActual = action.payload;
    },
    setCurrentCategory: (state, action) => {
      state.currentCategory = action.payload;
    },
  },
});

export const {
  setBudgetActuality,
  setCategoriesActuality,
  setConstrainstActuality,
  setTransactionActuality,
  setCurrentCategory,
} = dataActualitySlice.actions;
export default dataActualitySlice.reducer;
