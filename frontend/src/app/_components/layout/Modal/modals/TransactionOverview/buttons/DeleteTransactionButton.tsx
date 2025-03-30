import DeleteButton from '@/app/_components/common/DeleteButton';
import React from 'react';

export default function DeleteTransactionButton({
  id,
}: {
  id: number | string;
}) {
  const deleteTransactionReq = () => {
    console.log(id + 'deleted');
  };

  return <DeleteButton handleClick={deleteTransactionReq} />;
}
