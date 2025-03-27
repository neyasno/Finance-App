import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import React from 'react';
import ExitIcon from '../../../../../../../public/images/components/ExitIcon';

export default function LogoutButton() {
  const setModal = useModal();
  const openExitSuggestion = () => {
    setModal(ModalType.ExitSuggestion);
  };

  return (
    <button
      className="px-3 transition-all border-2 border-transparent rounded-full hover:border-black dark:hover:border-white"
      onClick={openExitSuggestion}
    >
      <div className="w-7">
        <ExitIcon />
      </div>
    </button>
  );
}
