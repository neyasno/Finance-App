import ThemesProvider from '@/utils/ThemesProvider';
import Header from '../_components/layout/Header/Header';
import Modal from '../_components/layout/Modal/Modal';
import { NextIntlClientProvider } from 'next-intl';
import { getMessages } from 'next-intl/server';
import VerificationWrapper from '../_components/layout/VerificationWrapper/VerificationWrapper';

export default async function LocaleLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  const messages = await getMessages();

  return (
    <NextIntlClientProvider messages={messages}>
      <ThemesProvider>
        <VerificationWrapper>
          <Modal />
          <header className="">
            <Header />
          </header>
          <main className="h-screen">{children}</main>
          <footer></footer>
        </VerificationWrapper>
      </ThemesProvider>
    </NextIntlClientProvider>
  );
}
