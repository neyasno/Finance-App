import { createSlice } from '@reduxjs/toolkit';

export enum EDragAndDropStatus {
  IN_PROCESS,
  COMPLETED,
  NONE,
}

type DragAndDropSlice = {
  from: string;
  to: string;
  status: EDragAndDropStatus;
};

const initialState: DragAndDropSlice = {
  from: '',
  to: '',
  status: EDragAndDropStatus.NONE,
};

const dragSlice = createSlice({
  name: 'drag',
  initialState: initialState,
  reducers: {
    setDragFrom: (state, action) => {
      state.from = action.payload;
    },
    setDragTo: (state, action) => {
      state.to = action.payload;
    },
    setDragStatus: (state, action) => {
      state.status = action.payload;
    },
  },
});

export const { setDragFrom, setDragStatus, setDragTo } = dragSlice.actions;
export default dragSlice.reducer;
