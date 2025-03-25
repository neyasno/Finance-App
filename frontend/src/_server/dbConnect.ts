import mongoose from 'mongoose';

const MONGODB_URI = 'mongodb://127.0.0.1:27017/taskboard';

const dbConnect = async () => {
  if (mongoose.connections[0].readyState) {
    console.log('Подключение к базе данных уже установлено');

    return;
  }

  try {
    await mongoose.connect(MONGODB_URI);

    console.log('Подключение к базе данных успешно');
  } catch (error) {
    console.error('Ошибка подключения к базе данных:', error);
  }
};

export default dbConnect;
