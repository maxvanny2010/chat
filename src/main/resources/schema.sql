DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS post;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS authorities;

CREATE TABLE IF NOT EXISTS authorities
(
    id_authority serial primary key,
    authority    varchar(50) unique not null
);

CREATE TABLE IF NOT EXISTS users
(
    id_user      serial primary key,
    username     varchar(2000) unique NOT NULL,
    password     varchar(2000)        NOT NULL,
    enable       boolean default true,
    authority_id int4                 NOT NULL
        references authorities (id_authority) ON DELETE CASCADE,
    UNIQUE (username, password)
);
CREATE TABLE IF NOT EXISTS room
(
    id_room serial primary key,
    created timestamp without time zone not null default now(),
    author  varchar(2000)               NOT NULL
        REFERENCES users (username) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
);
CREATE TABLE IF NOT EXISTS message
(
    id_message  serial primary key,
    description text,
    room_id     int4,
    created     timestamp without time zone not null unique default now(),
    author      varchar(2000)               NOT NULL
        REFERENCES users (username) ON DELETE CASCADE,
    CONSTRAINT room_id_fk FOREIGN KEY (room_id)
        REFERENCES room (id_room) ON DELETE CASCADE
);

DELETE
FROM message;
DELETE
FROM room;
DELETE
FROM users;
DELETE
FROM authorities;
insert into authorities(authority)
values ('ROLE_USER'),
       ('ROLE_ADMIN');
insert into users(username, password, authority_id)
values ('admin', '$2a$10$xgd7DC3.fpquD6blnjPBUuGmICZ2dEIblENDO6EnVY.sum0y2b8Wi',
        (select id_authority from authorities where authority = 'ROLE_ADMIN')),
       ('user', '$2a$10$xgd7DC3.fpquD6blnjPBUuGmICZ2dEIblENDO6EnVY.sum0y2b8Wi',
        (select id_authority from authorities where authority = 'ROLE_USER')),
       ('any', '$2a$10$xgd7DC3.fpquD6blnjPBUuGmICZ2dEIblENDO6EnVY.sum0y2b8Wi',
        (select id_authority from authorities where authority = 'ROLE_USER'));
insert into room(author, created)
values ('user', '2020-07-13 13:09'),
       ('any', '2020-07-13 13:10');
insert into message(description, author, room_id, created)
values ('hi i am user', 'user', 1, '2020-06-29 09:09:02'),
       ('hi i am any', 'any', 1, '2020-06-29 09:09:03'),
       ('hi i am admin', 'admin', 1, '2020-06-29 09:09:04'),
       ('hi i am user', 'user', 2, '2020-06-29 09:10:02'),
       ('hi i am any', 'any', 2, '2020-06-29 09:10:03'),
       ('hi i am admin', 'admin', 2, '2020-06-29 09:10:04');

