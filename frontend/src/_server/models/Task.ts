import mongoose, { Schema, Document } from 'mongoose';

export interface ITask extends Document {
  _id: string;
  title: string;
  description: string;
  isCompleted: boolean;
  priority: number;
}

const TaskSchema: Schema = new Schema({
  title: { type: String, required: true },
  description: { type: String },
  isCompleted: { type: Boolean },
  priority: { type: Number },
});

export const Task =
  mongoose.models.Task || mongoose.model<ITask>('Task', TaskSchema);
