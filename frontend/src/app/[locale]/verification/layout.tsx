import React from 'react';

export default function VerificationLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  return (
    <div className="w-full flex items-center justify-center flex-col mt-40">
      <form className="flex flex-col justify-items-center w-10/12 max-w-md gap-4 items-center">
        {children}
      </form>
    </div>
  );
}
