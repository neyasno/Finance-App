import type { Metadata } from 'next';
import { Fira_Mono } from 'next/font/google';
import './globals.css';
import ReduxProvider from '@/store/ReduxProvider';

const firaMono = Fira_Mono({
  weight: '400',
  subsets: ['latin', 'cyrillic'],
});

export const metadata: Metadata = {
  title: 'Koi',
  description: 'Your personal finance manager',
  icons: {
    icon: 'favicon.svg',
  },
};

export default async function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" suppressHydrationWarning>
      <body className={`${firaMono.className} antialiased`}>
        <ReduxProvider>{children}</ReduxProvider>
      </body>
    </html>
  );
}
