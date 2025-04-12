import { TAnaliticUnit } from '@/app/_components/pages/analitics/section/StatisticBricks/GeneralBrick';

const calculateBudget = (
  dataUnits: TAnaliticUnit[]
): { all_income: number; all_outcome: number; budget: number } => {
  let all_income = 0;
  let all_outcome = 0;

  for (const unit of dataUnits) {
    all_income += unit.income;

    all_outcome += unit.outcome;
  }

  all_income = Math.abs(all_income);

  all_outcome = Math.abs(all_outcome);

  const budget = Math.abs(all_income - all_outcome);

  return { all_income, all_outcome, budget };
};

export default calculateBudget;
