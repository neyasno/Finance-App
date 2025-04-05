import { EApi } from '@/enums';
import fetchApi from '@/utils/fetchApi';
import React from 'react';
import { setTransactionActuality } from '@/store/slices/dataActualitySlice';
import { useAppDispatch } from '@/store/store';
import { ModalType } from '@/store/slices/modalSlice';
import { useModal } from '@/utils/hooks/useModal';
import TransactionForm from './TransactionForm';

export default function CreateTransactionForm() {
  const dispatch = useAppDispatch();
  const setModal = useModal();

  const [value, setValue] = React.useState(0);
  const [type, setType] = React.useState<'income' | 'outcome'>('income');
  const [title, setTitle] = React.useState('');
  const [category, setCategory] = React.useState('1');

  const createTransactionReq = async (
    e: React.MouseEvent<HTMLElement, MouseEvent>
  ) => {
    e.preventDefault();

    try {
      const newTransaction = {
        title,
        value,
        type,
        categoryId: category,
      };
      console.log(newTransaction);

      const res = await fetchApi(EApi.TRANSACTIONS, 'POST', newTransaction);
      console.log(res);

      dispatch(setTransactionActuality(true));

      setModal(ModalType.None);
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <>
      <TransactionForm
        category={category}
        setCategory={setCategory}
        setTitle={setTitle}
        setType={setType}
        setValue={setValue}
        title={title}
        transactionReq={createTransactionReq}
        type={type}
        value={value}
      />
    </>
  );
}
