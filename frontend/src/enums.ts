export enum EApi {
  DEFAULT = 'http://localhost:8080/api',
  USERS = DEFAULT + '/auth',

  LOGIN = USERS + '/login',
  VERIFICATION = USERS + '/verification',
  REGISTRATION = USERS + '/registration',

  TRANSACTIONS = DEFAULT + '/transactions',
  CATEGORIES = TRANSACTIONS + '/categories',
  CONSTRAINTS = DEFAULT + '/constraints',
  ANALYTICS = DEFAULT + '/analytics',
  ANALYTICS_GENERAL = ANALYTICS + '/general',
  ANALYTICS_INCOME = ANALYTICS + '/income',
  ANALYTICS_OUTCOME = ANALYTICS + '/outcome',
  ANALYTICS_CATEGORIES = ANALYTICS + '/categories',
}

export enum ERoutes {
  DEFAULT = '/',

  LOGIN = '/verification/login',
  REGISTRATION = '/verification/registration',
  RESTORE_PASSWORD_REQ = '/verification/restore_password_req',
  RESTORE_PASSWORD = '/verification/restore_password',

  ANALITICS = '/analitics',
}
