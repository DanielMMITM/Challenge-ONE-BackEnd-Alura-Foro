ALTER TABLE users
    CHANGE COLUMN name username VARCHAR(30) NOT NULL ,
    ADD UNIQUE INDEX username_UNIQUE (username ASC) VISIBLE;