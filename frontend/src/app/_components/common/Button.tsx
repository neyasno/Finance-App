import React from 'react';

type ButtonProps = {
  text: string;
  handleClick: (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => void;
};

export default function Button({ text, handleClick }: ButtonProps) {
  return (
    <button
      className="w-full py-2 border-2 transition 
                        px-1 md:px-3
                        text-center border-black 
                        hover:bg-black hover:text-white 
                        dark:border-white dark:hover:bg-white dark:hover:text-black "
      onClick={handleClick}
    >
      {text}
    </button>
  );
}
