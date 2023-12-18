create database zelu character set utf8mb4;

use zelu;
set character set utf8mb4;

# 用户
create table user
(
    username    BIGINT auto_increment primary key      not null,
    nickname   char(16)                            not null,
    password   char(100)                           not null,
    description varchar(255) default null        ,
    avatar_url varchar(255) default null         ,
    role       int        default 0                not null,
    create_at  bigint   not null comment '秒级',
    register_way  int        default 1 not null,
    dept_id    bigint     default 0                not null,
    deleted    tinyint(1) default 0                not null
)AUTO_INCREMENT = 200000000;


create index idx_username on user (username);
create index idx_time on user (create_at);
create index idx_dept_id on user (dept_id);

create table wechat_user
(
    username    bigint not null,
    openid      varchar(60) not null primary key,
    unionid     varchar(50),
    constraint openid
        unique (openid)
)AUTO_INCREMENT = 100000000;

create index idx_username on wechat_user (username);
create index idx_openid on wechat_user (openid);

# 学校信息
create table school
(
    school_id      bigint auto_increment
        primary key,
    school_name    varchar(30) not null,
    school_avatar  varchar(255) default null,
    school_desc    varchar(255) ,
    school_address varchar(255),
    phone varchar(20) not null,
    status  int     default 0    not null,
    create_at  BIGINT   not null comment '秒级',
    deleted    tinyint(1) default 0         not null
)AUTO_INCREMENT = 100000;

create index idx_school_id on school (school_id);
create index idx_time on school(create_at);

# 课程信息
create table course
(
    course_id     bigint auto_increment
        primary key,
    course_name   varchar(30) not null,
    course_avatar varchar(255) default null,
    course_desc   varchar(255),
    status int   default 0     not null,
    school_id    bigint      not null,
    create_at  BIGINT  not null comment '秒级',
    deleted    tinyint(1) default 0         not null
);

create index idx_course_id on course (course_id);
create index idx_time on course (create_at);

# 课程用户
create table course_user
(
    course_id bigint not null,
    username  bigint not null,
    role     int  default 0 not null ,
    deleted    tinyint(1) default 0         not null,
    primary key (course_id, username)
);

create index idx_course_user on course_user (course_id, username);
create index idx_role on course_user (role);

# 公司信息
create table company
(
    company_id      bigint auto_increment
        primary key,
    company_name    varchar(30) not null,
    company_avatar  varchar(255) default null,
    company_desc    varchar(255) ,
    company_address varchar(255)   ,
    create_at  BIGINT   not null comment '秒级',
    phone varchar(20) not null,
    status  int    default 0      not null,
    deleted    tinyint(1) default 0         not null
)AUTO_INCREMENT = 500000;

create index idx_company_id on company (company_id);
create index idx_time on company (create_at);

# 公司用户
create table company_user
(
    company_id bigint not null,
    username    bigint not null,
    role     int default 0 not null ,
    deleted    tinyint(1) default 0         not null,
    primary key (company_id, username)
);

create index idx_company_user on company_user (company_id, username);
create index idx_role on company_user (role);