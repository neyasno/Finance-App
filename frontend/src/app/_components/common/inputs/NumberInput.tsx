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
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let inputValue = e.target.value;

    if (!inputValue) {
      handleChange(0);

      return;
    }

    if (inputValue !== '0') {
      inputValue = inputValue.replace(/^0+/, '');
    }

    const numValue = Number(inputValue);

    if (min !== undefined && numValue < min) {
      handleChange(min);

      return;
    }

    if (max !== undefined && numValue > max) {
      handleChange(max);

      return;
    }

    handleChange(numValue || 0);
  };

  return (
    <input
      type="number"
      readOnly={readonly}
      className="px-3 py-2 border-2 w-full bg-transparent
                      border-black dark:border-white"
      value={value === 0 ? '' : String(value)}
      placeholder={placeholder}
      min={min}
      max={max}
      onChange={handleInputChange}
      onBlur={() => {
        if (min !== undefined && value < min) handleChange(min);
        if (max !== undefined && value > max) handleChange(max);
      }}
    />
  );
}
