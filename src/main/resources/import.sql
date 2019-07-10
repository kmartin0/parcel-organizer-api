# OAuth2 Clients password = secret
INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 authorities, access_token_validity, refresh_token_validity)
VALUES ('parcel-tracker-android', '$2a$12$o3dmbF3ElqPL1ApJ.9R/Qu7cVBMyV8pn80.HPFPdKO/jerqGJiXZe', 'all', 'password,refresh_token',
        'ROLE_CLIENT', 200000, 2592000);

# User - Password = pass
INSERT INTO user(id, password, username)
VALUES (1,  '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kevinmartin0'),
       (2, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'janblaak'),
       (3,  '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kmartin0@gmail.com'),
       (4, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kev');
