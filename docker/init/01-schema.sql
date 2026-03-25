USE jbtests;

CREATE TABLE IF NOT EXISTS contact (
    id        INT          NOT NULL AUTO_INCREMENT,
    firstname VARCHAR(255) NULL,
    lastname  VARCHAR(255) NULL,
    telephone VARCHAR(255) NULL,
    email     VARCHAR(255) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sample (
    id      INT          NOT NULL AUTO_INCREMENT,
    version INT          NULL,
    sample  VARCHAR(255) NULL,
    color   VARCHAR(255) NULL,
    PRIMARY KEY (id)
);
