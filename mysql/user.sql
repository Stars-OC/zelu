-- auto-generated definition
create table user
(
    username     bigint auto_increment
        primary key,
    nickname     char(16)             not null,
    password     char(100)            not null,
    has_avatar   tinyint(1) default 0 not null,
    role         int        default 1 not null,
    create_at    bigint               not null comment '秒级',
    deleted      tinyint(1) default 0 not null,
    register_way int        default 1 not null,
    constraint idx_username
        unique (username)
);

create table wechat_user
(
    username     bigint auto_increment
        primary key,
    openid     varchar(60)          not null unique,
    unionID     varchar(50)          not null,
    create_time datetime             not null,
    constraint idx_username
        unique (username)
);