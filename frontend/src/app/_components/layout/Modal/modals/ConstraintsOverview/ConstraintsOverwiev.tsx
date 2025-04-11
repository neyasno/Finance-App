import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import { ModalType } from '@/store/slices/modalSlice';
import fetchApi from '@/utils/fetchApi';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';

export default function ConstraintsOverwiev() {
  const t = useTranslations('home.content.transactions');

  const [constraints, setConstraints] = React.useState([]);
  const setModal = useModal();

  const getConstraintsReq = async () => {
    try {
      const res = await fetchApi(EApi.CONSTRAINTS, 'GET');
      console.log(res);

      setConstraints(res);
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    getConstraintsReq();
  }, []);

  return (
    <div className="flex flex-col">
      <div>{constraints}</div>
      <div className="flex">
        <Button
          handleClick={() => {
            setModal(ModalType.CreateConstraint);
          }}
          text={t('create')}
        />
      </div>
    </div>
  );
}
