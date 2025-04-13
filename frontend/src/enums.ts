const apiUrl = 'http://192.168.100.29:8080';

export const EApi = {
  DEFAULT: apiUrl + '/api',
  USERS: apiUrl + '/api/auth',

  LOGIN: apiUrl + '/api/auth/login',
  VERIFICATION: apiUrl + '/api/auth/verification',
  REGISTRATION: apiUrl + '/api/auth/registration',

  TRANSACTIONS: apiUrl + '/api/transactions',
  CATEGORIES: apiUrl + '/api/transactions/categories',
  CONSTRAINTS: apiUrl + '/api/constraints',
  ANALYTICS: apiUrl + '/api/analytics',
  ANALYTICS_GENERAL: apiUrl + '/api/analytics/general',
  ANALYTICS_INCOME: apiUrl + '/api/analytics/income',
  ANALYTICS_OUTCOME: apiUrl + '/api/analytics/outcome',
  ANALYTICS_CATEGORIES: apiUrl + '/api/analytics/categories',
} as const;

export enum ERoutes {
  DEFAULT = '/',

  LOGIN = '/verification/login',
  REGISTRATION = '/verification/registration',
  RESTORE_PASSWORD_REQ = '/verification/restore_password_req',
  RESTORE_PASSWORD = '/verification/restore_password',

  ANALITICS = '/analitics',
}
