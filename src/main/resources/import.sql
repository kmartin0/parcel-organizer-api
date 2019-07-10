INSERT INTO oauth_client_details
(client_id, client_secret, scope, authorized_grant_types,
 authorities, access_token_validity, refresh_token_validity)
VALUES ('parcel-tracker-android', '$2a$12$o3dmbF3ElqPL1ApJ.9R/Qu7cVBMyV8pn80.HPFPdKO/jerqGJiXZe', 'all',
        'password,refresh_token',
        'ROLE_CLIENT', 200000, 2592000);


INSERT INTO user(id, password, username)
VALUES (1, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kevinmartin0'),
       (2, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'janblaak'),
       (3, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kmartin0@gmail.com'),
       (4, '$2a$12$Uv6GCKwJk7SaEPpy/h/dM.Qf4/BE5OTLc.31cPYrPc/Sl/LhUO1GO', 'kev');


INSERT INTO parcel_status(id, status)
VALUES (1, 'ORDERED'),
       (2, 'SENT'),
       (3, 'DELIVERED');


INSERT INTO parcel(courier, last_updated, sender, title, tracking_url, parcel_status, user)
VALUES ('dpd', '2019-07-10 17:41:36', 'Bol', 'Iphone', 'dpd.com', 1, 4),
       ('postnl', '2019-07-10 17:41:36', 'Zalando', 'Shoes', 'postnl.nl', 3, 4),
       ('dhl', '2019-07-10 17:41:36', 'Wehkamp', 'Clothes', 'dhl.com', 2, 4);
