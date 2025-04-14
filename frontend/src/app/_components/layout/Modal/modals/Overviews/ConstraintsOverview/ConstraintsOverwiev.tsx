import Button from '@/app/_components/common/Button';
import { EApi } from '@/enums';
import { ModalType } from '@/store/slices/modalSlice';
import fetchApi from '@/utils/fetchApi';
import { useModal } from '@/utils/hooks/useModal';
import { useTranslations } from 'next-intl';
import React, { useEffect } from 'react';
import Constraint from './Constraint';

export type RConstraint = {
  available: number;
  id: number;
  categoryId: number | null;
  timeCreated: string;
  timeToExpire: string;
  value: number;
};

export default function ConstraintsOverwiev() {
  const t = useTranslations('home.content.transactions');

  const [constraints, setConstraints] = React.useState<RConstraint[]>([]);
  const setModal = useModal();

  const getConstraintsReq = async () => {
    try {
      const res: RConstraint[] = await fetchApi(EApi.CONSTRAINTS, 'GET');
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
      <ul className="flex flex-col gap-1">
        {constraints.length > 0 ? (
          constraints.map((c, index) => (
            <li key={index}>
              <Constraint constraintData={c} />
            </li>
          ))
        ) : (
          <p className="w-full text-center italic py-2 text-gray_d dark:text-gray_l">
            {t('no_constraints')}...
          </p>
        )}
      </ul>
      <div className="flex mt-2">
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
