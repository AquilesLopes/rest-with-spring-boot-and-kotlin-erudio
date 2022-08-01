CREATE SEQUENCE person_seq;

CREATE TABLE IF NOT EXISTS person (
    id bigint NOT NULL DEFAULT NEXTVAL ('person_seq'),
    address varchar(255) NOT NULL,
    first_name varchar(80) NOT NULL,
    gender varchar(50) NOT NULL,
    last_name varchar(80) NOT NULL,
    birth_day timestamp(0) DEFAULT NULL,
    PRIMARY KEY (id)
    )


