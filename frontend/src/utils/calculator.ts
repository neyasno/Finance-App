export const getDifference = (
  income: number,
  outcome: number
): { difference: number; type: 'income' | 'outcome' } => {
  const max = Math.max(income, outcome);
  if (max === income) {
    return { difference: income - outcome, type: 'income' };
  }
  return { difference: outcome - income, type: 'outcome' };
};
