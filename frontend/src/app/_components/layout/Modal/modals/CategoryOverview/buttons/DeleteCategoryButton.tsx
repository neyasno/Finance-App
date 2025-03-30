import DeleteButton from '@/app/_components/common/DeleteButton';
import React from 'react';

export default function DeleteCategoryButton({ id }: { id: number | string }) {
  const deleteCategoryReq = () => {
    console.log(id + 'deleted');
  };

  return <DeleteButton handleClick={deleteCategoryReq} />;
}
