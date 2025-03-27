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

  ANALITICS = '/analitics',
}
