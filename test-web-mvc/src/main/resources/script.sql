CREATE TABLE USERS (
    ID       BIGINT NOT NULL,
    USERNAME    VARCHAR(30),
    PASSWORD    VARCHAR(100),
    EXPIRES  BIGINT
);

ALTER TABLE USERS ADD CONSTRAINT PK_USERS PRIMARY KEY (ID);