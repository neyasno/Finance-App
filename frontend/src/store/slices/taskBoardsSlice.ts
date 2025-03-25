import { createSlice } from '@reduxjs/toolkit';

export enum ContentStatus {
  ACTUAL,
  NOT_ACTUAL,
}

type TaskBoardsSlice = {
  currentTaskBoardId: string;
  currentContainerId: string;
  currentTaskId: string;
  taskBoardsStatus: ContentStatus;
  taskContainersStatus: ContentStatus;
  tasksStatus: ContentStatus;
};

const initialState: TaskBoardsSlice = {
  currentTaskBoardId: '0',
  currentContainerId: '0',
  currentTaskId: '0',
  taskBoardsStatus: ContentStatus.ACTUAL,
  taskContainersStatus: ContentStatus.ACTUAL,
  tasksStatus: ContentStatus.ACTUAL,
};

const taskBoardsSlice = createSlice({
  name: 'taskBoards',
  initialState: initialState,
  reducers: {
    setCurrentTaskBoard: (state, action) => {
      state.currentTaskBoardId = action.payload;
    },
    setCurrentContainer: (state, action) => {
      state.currentContainerId = action.payload;
    },
    setCurrentTask: (state, action) => {
      state.currentTaskId = action.payload;
    },
    setTasksStatus: (state, action) => {
      state.tasksStatus = action.payload;
    },
    setTaskBoardsStatus: (state, action) => {
      state.taskBoardsStatus = action.payload;
    },
    setContainerStatus: (state, action) => {
      state.taskContainersStatus = action.payload;
    },
  },
});

export const {
  setCurrentTaskBoard,
  setCurrentContainer,
  setCurrentTask,
  setContainerStatus,
  setTaskBoardsStatus,
  setTasksStatus,
} = taskBoardsSlice.actions;
export default taskBoardsSlice.reducer;
