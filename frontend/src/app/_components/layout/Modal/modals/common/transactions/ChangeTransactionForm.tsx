import React from 'react';
import TransactionForm from './TransactionForm';
import { ModalType } from '@/store/slices/modalSlice';
import { setTransactionActuality } from '@/store/slices/dataActualitySlice';
import fetchApi from '@/utils/fetchApi';
import { EApi } from '@/enums';
import { useModal } from '@/utils/hooks/useModal';
import { useAppDispatch } from '@/store/store';
import useTransactionOverview from '@/utils/hooks/useTransactionOverview';

export default function ChangeTransactionForm() {
  const dispatch = useAppDispatch();
  const { transaction } = useTransactionOverview();
  const setModal = useModal();

  const [value, setValue] = React.useState(transaction.value);
  const [type, setType] = React.useState<'income' | 'outcome'>(
    transaction.type
  );
  const [title, setTitle] = React.useState(transaction.title);
  const [categoryId, setCategory] = React.useState(transaction.categoryId);

  const changeTransactionReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    try {
      const newTransaction = {
        title,
        value,
        type,
        categoryId,
      };
      console.log(newTransaction);

      const res = await fetchApi(
        EApi.TRANSACTIONS + '/' + transaction.id,
        'PUT',
        newTransaction
      );
      console.log(res);

      dispatch(setTransactionActuality(false));

      setModal(ModalType.None);
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <>
      <TransactionForm
        category={categoryId}
        setCategory={setCategory}
        setTitle={setTitle}
        setType={setType}
        setValue={setValue}
        title={title}
        transactionReq={changeTransactionReq}
        type={type}
        value={value}
      />
    </>
  );
}
