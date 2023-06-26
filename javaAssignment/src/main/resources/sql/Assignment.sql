----------------CREATE SCHEMA------------------
CREATE SCHEMA JavaAssignmentDB;


----------------CREATE Table------------------

CREATE TABLE customer_tbl (
    id bigint,
    customerid varchar(10),
    short_name varchar(20),
    full_name varchar(60),
    address1 varchar(80),
    address2 varchar(80),
    address3 varchar(80),
    city varchar(60),
    postal_code varchar(10) NOT NULL,
    PRIMARY KEY (id)
);

--Select * FROM customer_tbl;

--TRUNCATE customer_tbl;
