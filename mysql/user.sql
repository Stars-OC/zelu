-- auto-generated definition
create table user
(
    username        BIGINT auto_increment primary key      not null,
    nickname   char(16)                            not null,
    password   char(100)                           not null,
    avatar_url varchar(255) default null         ,
    role       int        default 1                not null,
    create_at  BIGINT  default now() not null comment '秒级',
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
    unionid     varchar(50) ,
    constraint idx_username
        unique (username),
    constraint openid
        unique (openid)
)AUTO_INCREMENT = 100000000;