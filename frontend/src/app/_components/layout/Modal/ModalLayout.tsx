import { ModalType, setModalType } from '@/store/slices/modalSlice';
import { useAppDispatch } from '@/store/store';
import { ReactNode } from 'react';

export default function ModalLayout({ children }: { children: ReactNode }) {
  const dispatcher = useAppDispatch();

  const closeModal = () => {
    dispatcher(setModalType(ModalType.None));
  };

  return (
    <div
      className={`z-50 w-screen h-screen bg-modal flex items-center justify-center fixed`}
      onClick={closeModal}
    >
      <div
        className="bg-white dark:bg-black w-80 p-5"
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );
}
