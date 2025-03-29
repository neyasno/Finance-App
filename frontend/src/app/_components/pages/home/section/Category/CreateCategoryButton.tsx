import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import React from 'react';

export default function CreateCategoryButton() {
  const setModal = useModal();
  return (
    <button
      className="flex border-1 h-1/2 w-1/3 rounded-md justify-center items-center 
              border-black dark:border-white hover:bg-white_d dark:hover:bg-black_l"
      onClick={() => setModal(ModalType.CreateCategory)}
    >
      <p>+</p>
    </button>
  );
}
