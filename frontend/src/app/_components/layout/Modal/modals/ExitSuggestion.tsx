import Button from '@/app/_components/common/Button';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import React from 'react';

export default function ExitSuggestion() {
  const setModal = useModal();

  const logOut = () => {
    setModal(ModalType.None);
  };

  return (
    <div className="flex flex-col justify-center items-center gap-5">
      <p className="text-lg">You sure want to exit?</p>
      <div className="w-1/2">
        <Button text="Exit" handleClick={logOut} />
      </div>
    </div>
  );
}
