create table user
(
    id        int auto_increment
        primary key,
    password  varchar(255) not null,
    email     varchar(255) not null unique ,
    isActive  tinyint(1)   not null,
    firstname varchar(255) null,
    lastname  varchar(255) null
);