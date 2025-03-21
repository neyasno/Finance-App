import { fileURLToPath } from 'url';
import { FlatCompat } from '@eslint/eslintrc';
import path from 'path';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const compat = new FlatCompat({
  baseDirectory: __dirname,
  recommendedConfig: {
    'eslint:recommended': true,
  },
});

const eslintConfig = compat.config({
  extends: [
    'next/core-web-vitals',
    'next/typescript',
    'plugin:prettier/recommended',
  ],
  rules: {
    quotes: ['warn', 'single'],
    'lines-around-directive': ['warn', { before: 'always', after: 'always' }],
    'prettier/prettier': ['warn'],
    'padding-line-between-statements': [
      'warn',
      { blankLine: 'always', prev: 'directive', next: '*' },
      { blankLine: 'always', prev: 'block', next: '*' },
      { blankLine: 'always', prev: 'expression', next: '*' },
    ],
  },
});

export default eslintConfig;
