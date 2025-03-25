import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import Image from 'next/image';
import React from 'react';

export default function LogoutButton() {
  const setModal = useModal();
  const openExitSuggestion = () => {
    setModal(ModalType.ExitSuggestion);
  };

  return (
    <button
      className="px-3 transition-all border-2 border-transparent rounded-full hover:border-black"
      onClick={openExitSuggestion}
    >
      <Image alt="exit-image" src={'/images/exit.svg'} width={25} height={25} />
    </button>
  );
}
