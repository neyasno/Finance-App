'use client';

import { ModalType } from '@/store/slices/modalSlice';
import { useAppSelector } from '@/store/store';
import React from 'react';
import ModalLayout from './ModalLayout';
import ChangeConstraintForm from './modals/ChangeConstraintForm';
import CreateConstraintForm from './modals/CreateConstraintForm';
import CreateTransantionForm from './modals/CreateTransantionForm';
import DeleteSuggestion from './modals/DeleteSuggestion';
import ChangeTransactionForm from './modals/ChangeTransactionForm';
import ExitSuggestion from './modals/ExitSuggestion';
import TransactionOverview from './modals/TransactionOverview/TransactionOverview';

export default function Modal() {
  const state = useAppSelector((state) => state.modal);

  switch (state.type) {
    case ModalType.None:
      return <></>;

    case ModalType.ChangeConstraint: {
      return (
        <ModalLayout>
          <ChangeConstraintForm />
        </ModalLayout>
      );
    }

    case ModalType.CreateConstraint: {
      return (
        <ModalLayout>
          <CreateConstraintForm />
        </ModalLayout>
      );
    }

    case ModalType.ChangeTransaction: {
      return (
        <ModalLayout>
          <ChangeTransactionForm />
        </ModalLayout>
      );
    }

    case ModalType.CreateTransaction: {
      return (
        <ModalLayout>
          <CreateTransantionForm />
        </ModalLayout>
      );
    }

    case ModalType.DeleteSuggestion: {
      return (
        <ModalLayout>
          <DeleteSuggestion />
        </ModalLayout>
      );
    }

    case ModalType.ExitSuggestion: {
      return (
        <ModalLayout>
          <ExitSuggestion />
        </ModalLayout>
      );
    }

    case ModalType.TransactionOverview: {
      return (
        <ModalLayout contentWidth="w-96">
          <TransactionOverview />
        </ModalLayout>
      );
    }

    default:
      throw new Error('NO SUCH MODAL TYPE EXISTS. HOW YOU DID IT?');
  }
}
