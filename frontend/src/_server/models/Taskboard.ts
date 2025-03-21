import mongoose, { Schema, Document } from "mongoose";

export enum ETaskBoardStatus{
    EXPIRED = "EXPIRED" ,
    IN_WORK = "IN_WORK" ,
    COMPLETED = "COMPLETED" , 
}

export interface ITaskBoard extends Document {
  _id : string;
  title: string ;
  status : ETaskBoardStatus , 
  users : string[] ,
  admins : string[] , 
  taskContainers? : string[] ;
}

const TaskBoardSchema: Schema = new Schema(
  {

    title : {type : String , required : true } ,
    status : {type : String } ,
    users : [{ type: mongoose.Schema.Types.ObjectId, ref: "User" }],
    admins : [{ type: mongoose.Schema.Types.ObjectId, ref: "User" }],
    taskContainers :[{ type: mongoose.Schema.Types.ObjectId, ref: "TaskContainer" }]

  }
);

export const TaskBoard = mongoose.models.TaskBoard || mongoose.model<ITaskBoard>("TaskBoard", TaskBoardSchema);
