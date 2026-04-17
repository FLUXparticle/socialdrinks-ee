# Auth0 Demo

## Link zum Login

```
https://dev-yozl2ey472f822zh.eu.auth0.com/authorize?
response_type=code&
client_id=p49CgVHHEb3324B7nuERA0642yR9mhQ4&
redirect_uri=http://localhost:8083/api/callback&
audience=jwt-audience&
scope=openid%20profile%20email&
state=xyzABC123
```

[Login](https://dev-yozl2ey472f822zh.eu.auth0.com/authorize?response_type=code&client_id=p49CgVHHEb3324B7nuERA0642yR9mhQ4&redirect_uri=http://localhost:8083/api/callback&audience=jwt-audience&scope=openid%20profile%20email&state=xyzABC123)

```
Username: developer@example.com
Password: Secret123

Username: admin@example.com
Password: Secret123
```

## Code gegen Token eintauschen

Der Code aus dem Redirect muss kopiert in diese URL eingesetzt werden.

```bash
curl --request POST \
  --url https://dev-yozl2ey472f822zh.eu.auth0.com/oauth/token \
  --header 'content-type: application/x-www-form-urlencoded' \
  --data 'grant_type=authorization_code' \
  --data 'client_id=p49CgVHHEb3324B7nuERA0642yR9mhQ4' \
  --data 'client_secret=CLIENT_SECRET' \
  --data 'code=DER_KOPIERTE_CODE' \
  --data 'redirect_uri=http://localhost:8083/api/callback' \
  --data 'state=xyzABC123'
```

## Der Token kann automatisch verifziert werden, weil der Server seinen öffentlichen Schlüssel unter einer Standard-URL bereitstellt

[https://dev-yozl2ey472f822zh.eu.auth0.com/.well-known/jwks.json](https://dev-yozl2ey472f822zh.eu.auth0.com/.well-known/jwks.json)

## Zugriff auf Auth-Server

Den erhaltenen `access_token` zuerst in eine Shell-Variable legen:

```bash
TOKEN='HIER_DEN_ACCESS_TOKEN_EINSETZEN'
```

Öffentlicher Endpunkt:

```bash
curl http://localhost:8083/api/auth/public
```

Claims des Tokens anzeigen:

```bash
curl \
  --header "Authorization: Bearer $TOKEN" \
  http://localhost:8083/api/auth/claims
```

Benutzer-Endpunkt:

```bash
curl \
  --header "Authorization: Bearer $TOKEN" \
  http://localhost:8083/api/auth/user
```

Customer-Endpunkt:

```bash
curl \
  --header "Authorization: Bearer $TOKEN" \
  http://localhost:8083/api/auth/customer
```

Admin-Endpunkt:

```bash
curl \
  --header "Authorization: Bearer $TOKEN" \
  http://localhost:8083/api/auth/admin
```
