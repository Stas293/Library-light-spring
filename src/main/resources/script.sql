CREATE TABLE human (
                       id BIGINT GENERATED ALWAYS AS IDENTITY,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       birthdate DATE NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE (first_name, last_name)
);

CREATE TABLE book (
                      id BIGINT GENERATED ALWAYS AS IDENTITY,
                      title VARCHAR(255) NOT NULL,
                      author VARCHAR(255) NOT NULL,
                      published DATE NOT NULL,
                      owner_id BIGINT REFERENCES human(id) ON DELETE SET NULL,
                      date_requested TIMESTAMP,
                      PRIMARY KEY (id)
);