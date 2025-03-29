import React from 'react';

export default function ContsraintValue({ value }: { value: number }) {
  return (
    <div className={'flex gap-1'}>
      <p>{value}</p>
      <div className={'size-2 border-1 border-green'}></div>
    </div>
  );
}
