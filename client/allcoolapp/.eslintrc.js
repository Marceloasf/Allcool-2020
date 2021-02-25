module.exports = {
  extends: ['prettier', 'react-app'],
  plugins: ['prettier', 'import', 'react-hooks'],
  parser: '@typescript-eslint/parser',
  env: {
    jest: true,
  },
  rules: {
    'prettier/prettier': 'error',
    'react-hooks/rules-of-hooks': 'error',
    'react-hooks/exhaustive-deps': 'warn',
    'no-restricted-globals': 'off',
    'import/first': 'off',
  },
  settings: {
    'import/resolver': {
      node: {
        extensions: ['.js', '.jsx', 'ts', 'tsx'],
      },
    },
  },
};
