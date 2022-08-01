CREATE SEQUENCE users_seq;

CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL DEFAULT NEXTVAL ('users_seq'),
    user_name varchar(255) DEFAULT NULL,
    full_name varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    account_non_expired boolean(1) DEFAULT NULL,
    account_non_locked boolean(1) DEFAULT NULL,
    credentials_non_expired boolean(1) DEFAULT NULL,
    enabled boolean(1) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_user_name UNIQUE  (user_name)
    );