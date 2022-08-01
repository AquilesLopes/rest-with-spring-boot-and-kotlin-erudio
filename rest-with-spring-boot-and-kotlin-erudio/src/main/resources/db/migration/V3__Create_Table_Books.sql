CREATE SEQUENCE books_seq;

CREATE TABLE books (
   id bigint NOT NULL DEFAULT NEXTVAL ('books_seq'),
   author text NOT NULL,
   launch_date timestamp(6),
   price decimal(65,2) NOT NULL,
   title text  NOT NULL,
   PRIMARY KEY (id)
)
