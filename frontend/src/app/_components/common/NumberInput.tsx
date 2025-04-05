import React from 'react';

type NumberInputProps = {
  handleChange: (value: number) => void;
  value: number;
  placeholder: string;
  readonly?: boolean;
  min?: number;
  max?: number;
};

export default function NumberInput({
  value,
  placeholder,
  readonly,
  handleChange,
  min,
  max,
}: NumberInputProps) {
  return (
    <input
      type="number"
      readOnly={readonly}
      className="px-3 py-2 border-2 w-full bg-transparent
                      border-black dark:border-white"
      value={value}
      placeholder={placeholder}
      min={min}
      max={max}
      onChange={(e) => handleChange(Number(e.target.value))}
    />
  );
}
