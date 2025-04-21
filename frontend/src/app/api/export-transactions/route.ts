// app/api/export-transactions/route.ts
import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';

export async function POST(req: Request) {
  console.log('POST /api/export-transactions');

  const transactions: TransactionProps[] = await req.json();

  if (!transactions || transactions.length === 0) {
    return new Response('No transactions provided', { status: 400 });
  }

  const header = ['id', 'title', 'value', 'type', 'time', 'categoryId'];
  const rows = transactions.map((tx: TransactionProps) =>
    [
      tx.id,
      `"${tx.title}"`,
      tx.value,
      tx.type,
      `"${tx.time}"`,
      tx.categoryId,
    ].join(',')
  );

  const csv = [header.join(','), ...rows].join('\n');

  return new Response(csv, {
    status: 200,
    headers: {
      'Content-Type': 'text/csv; charset=utf-8',
      'Content-Disposition': 'attachment; filename="transactions.csv"',
    },
  });
}
