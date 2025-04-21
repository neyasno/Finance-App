// app/api/export-transactions/route.ts
import { cookies } from 'next/headers';
import { EApi } from '@/enums';
import { TransactionProps } from '@/app/_components/pages/home/aside/TransactionHistory/TransactionContainer/Transaction';

export async function GET() {
  console.log('GET /api/export-transactions');

  const cookieStore = await cookies();
  const token = cookieStore.get('token')?.value;

  if (!token) {
    return new Response('Unauthorized', { status: 401 });
  }

  const apiRes = await fetch(`${EApi.TRANSACTIONS}?page=0&size=200`, {
    method: 'GET',
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!apiRes.ok) {
    const text = await apiRes.text();
    console.error('Ошибка при запросе транзакций:', apiRes.status, text);

    return new Response('Ошибка при загрузке транзакций', { status: 500 });
  }

  const { content } = await apiRes.json();

  const header = ['id', 'title', 'value', 'type', 'time', 'categoryId'];
  const rows = content.map((tx: TransactionProps) =>
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
