create database zelu character set utf8mb4;
use zelu;
set character set utf8mb4;

# 用户
create table user
(
    username        BIGINT auto_increment primary key      not null,
    nickname   char(16)                            not null,
    password   char(100)                           not null,
    description varchar(255) default null        ,
    avatar_url varchar(255) default null         ,
    role       int        default 1                not null,
    create_at  BIGINT  default now() not null comment '秒级',
    register_way  int        default 1 not null,
    dept_id    bigint     default 0                not null,
    deleted    tinyint(1) default 0                not null
)AUTO_INCREMENT = 200000000;

create index idx_username on user (username);
create index idx_time on user (create_at);
create index idx_dept_id on user (dept_id);

-- auto-generated definition
create table wechat_user
(
    username    bigint auto_increment
        primary key,
    openid      varchar(60) not null,
    unionid     varchar(50) ,
    constraint openid
        unique (openid)
)AUTO_INCREMENT = 100000000;

create index idx_username on wechat_user (username);
create index idx_openid on wechat_user (openid);

create table school_info
(
    school_id      bigint auto_increment
        primary key,
    school_name    varchar(30) not null,
    school_avatar  varchar(255) default null,
    school_desc    int          not null,
    school_address text     ,
    status  int          not null,
    create_at  BIGINT  default now() not null comment '秒级',
    deleted    tinyint(1) default 0         not null
)AUTO_INCREMENT = 100000;

create index idx_school_id on school_info (school_id);
create index idx_time on school_info (create_at);

create table class_info
(
    class_id     bigint auto_increment
        primary key,
    class_name   varchar(30) not null,
    class_avatar varchar(255) default null,
    class_desc   int         not null,
    status int         not null,
    school_id    bigint      not null,
    create_at  BIGINT  default now() not null comment '秒级',
    deleted    tinyint(1) default 0         not null,
    constraint fk_school_id
        foreign key (school_id) REFERENCES school_info (school_id)
);

create index idx_class_id on class_info (class_id);
create index idx_time on class_info (create_at);

create table class_user
(
    class_id bigint not null,
    user_id  bigint not null,
    deleted    tinyint(1) default 0         not null,
    primary key (class_id, user_id),
    constraint fk_class_id
        foreign key (class_id) references class_info (class_id),
    constraint fk_user_id
        foreign key (user_id) references user (username)
);

create index idx_class_user on class_user (class_id, user_id);

create table company_info
(
    company_id      bigint auto_increment
        primary key,
    company_name    varchar(30) not null,
    company_avatar  varchar(255) default null,
    company_desc    int          not null,
    company_address text    ,
    create_at  BIGINT  default now() not null comment '秒级',
    status  int          not null,
    deleted    tinyint(1) default 0         not null
)AUTO_INCREMENT = 200000;

create index idx_company_id on company_info (company_id);
create index idx_time on company_info (create_at);

create table company_user
(
    company_id bigint not null,
    user_id    bigint not null,
    deleted    tinyint(1) default 0         not null,
    primary key (company_id, user_id),
    constraint fk_company_id
        foreign key (company_id) references company_info (company_id),
    constraint fk_user_id
        foreign key (user_id) references user (username)
);

create index idx_company_user on company_user (company_id, user_id);