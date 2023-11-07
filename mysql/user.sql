-- auto-generated definition
create table user
(
    username        BIGINT auto_increment primary key      not null,
    nickname   char(16)                            not null,
    password   char(100)                           not null,
    has_avatar tinyint(1) default 0                not null,
    role       int        default 1                not null,
    create_at  BIGINT  not null comment '秒级',
    deleted    tinyint(1) default 0                not null,
    register_way  int        default 1 not null,
    constraint idx_username
        unique (username)
)AUTO_INCREMENT = 200000000;

-- auto-generated definition
create table wechat_user
(
    username    bigint auto_increment
        primary key,
    openid      varchar(60) not null,
    unionID     varchar(50) not null,
    create_time BIGINT  not null comment '秒级',
    constraint idx_username
        unique (username),
    constraint openid
        unique (openid)
)AUTO_INCREMENT = 100000000;