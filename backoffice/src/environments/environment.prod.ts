import {KeycloakConfig} from 'keycloak-js';

const keycloakConfig: KeycloakConfig = {
  realm: 'mobilemoney',
  url: 'http://192.168.1.6:9007/auth',
  clientId: 'publicmobilepaiement'
};
export const environment = {
  production: true,
  baseUrl: 'http://192.168.1.6:9002',
  key: keycloakConfig
};
