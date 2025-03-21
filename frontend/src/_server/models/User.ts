import mongoose, { Schema, Document } from 'mongoose';

export interface IUser extends Document {
  _id: string;
  email: string;
  password: string;
  avatarUrl: string;
  taskBoards: string[];
}

const UserSchema: Schema = new Schema({
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  avatarUrl: { type: String, required: false },
  taskBoards: [{ type: mongoose.Schema.Types.ObjectId, ref: 'TaskBoard' }],
});

export const User =
  mongoose.models.User || mongoose.model<IUser>('User', UserSchema);
