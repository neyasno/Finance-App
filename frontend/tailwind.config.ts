import type { Config } from 'tailwindcss';

export default {
  darkMode: 'class',
  content: [
    './src/pages/**/*.{js,ts,jsx,tsx,mdx}',
    './src/components/**/*.{js,ts,jsx,tsx,mdx}',
    './src/app/**/*.{js,ts,jsx,tsx,mdx}',
  ],
  theme: {
    extend: {
      colors: {
        modal: 'rgba(0 , 0 , 0 ,0.6)',

        link: 'rgba(100 , 100 , 200 , 1 )',
        warn: 'rgba(200 , 100 ,100 , 1)',

        black: 'rgba(35, 35, 35)',
        black_l: 'rgba(60, 60, 60)',
        black_ll: 'rgba(80, 80, 80)',
        black_d: 'rgba(25, 25, 25)',

        white: 'rgba(255, 255, 255)',
        white_d: 'rgba(190, 190, 190)',
        white_dd: 'rgba(145, 145, 145)',
        white_c: 'rgba(190, 190, 220)',

        gray: 'rgba(190,190,190)',
        green: 'rgba(40,170,40)',
        red: 'rgba(170,50,50)',
      },
    },
  },
  plugins: [],
} satisfies Config;
