import { createSlice } from "@reduxjs/toolkit";

type Task ={
    title : string ,
    text : string , 
    isActive : boolean 
}

type User = {
    email: string;
    password : string;
    tasks : Task[];
    isLogined : boolean
}

const initialState : User = {
    email : "" ,
    password : "" ,
    tasks : [] ,
    isLogined : false , 
}

const userSlice = createSlice({
    name : "user" ,
    initialState: initialState ,
    reducers : {
        setIsLogined : (state , action) => {
            state.isLogined = action.payload
        },
        setTasks : (state , action) =>{
            state.tasks = action.payload
        },
    }
})

export const { setIsLogined , setTasks } = userSlice.actions;
export default userSlice.reducer;