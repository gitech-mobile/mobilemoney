const keycloakConfig = {
  realm: 'mobilepaiement',
  url: 'http://192.168.215.1:9007/auth',
  clientId: 'mobilepaiement',
  credentials: {
    secret: 'fb6ff1f7-c69f-4bd0-9c78-582b441c587a'
  }
};
export const environment = {
  production: false,
  baseUrl: 'http://localhost:9002',
  keyConfig: keycloakConfig
};
