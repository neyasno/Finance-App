export enum EApi {
  DEFAULT = '/api',

  USERS = DEFAULT + '/users',

  LOGIN = USERS + '/login',
  VERIFICATION = USERS + '/verification',
  REGISTRATION = USERS + '/registration',

  TASKBOARDS = DEFAULT + '/taskboards',
  TASKBOARD = DEFAULT + '/taskboards/',

  TACK_CONTAINER = DEFAULT + '/taskcontainer',
}

export enum ERoutes {
  DEFAULT = '/',

  LOGIN = '/verification/login',
  REGISTRATION = '/verification/registration',
  RESTORE_PASSWORD_REQ = '/verification/restore_password_req',
  RESTORE_PASSWORD = '/verification/restore_password',

  ANALITICS = '/analitics',
}
