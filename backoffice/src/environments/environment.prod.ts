import {KeycloakConfig} from 'keycloak-js';

const keycloakConfig: KeycloakConfig = {
  realm: 'atps',
  url: 'http://192.168.215.1:9007/auth',
  clientId: 'mobilepaiement'
};
export const environment = {
  production: true,
  baseUrl: 'http://localhost:9002',
  key: keycloakConfig
};
