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
    create_at  bigint   not null comment '秒级',
    register_way  int        default 1 not null,
    dept_id    bigint     default 0                not null,
    deleted    tinyint(1) default 0                not null
)AUTO_INCREMENT = 200000000;


create index idx_username on user (username);
create index idx_time on user (create_at);
create index idx_dept_id on user (dept_id);

# 微信用户 TODO 将这个与user链接
create table wechat_user
(
    username    bigint not null,
    openid      varchar(60) not null primary key,
    unionid     varchar(50) ,
    constraint openid
        unique (openid),
    constraint fk_username
        foreign key (username) REFERENCES user (username)
)AUTO_INCREMENT = 100000000;

create index idx_username on wechat_user (username);
create index idx_openid on wechat_user (openid);

# 学校信息
create table school_info
(
    school_id      bigint auto_increment
        primary key,
    school_name    varchar(30) not null,
    school_avatar  varchar(255) default null,
    school_desc    int          not null,
    school_address text     ,
    status  int          not null,
    create_at  BIGINT   not null comment '秒级',
    deleted    tinyint(1) default 0         not null,
    constraint fk_school_dept_id
        foreign key (school_id) references user (dept_id)
)AUTO_INCREMENT = 100000;

create index idx_school_id on school_info (school_id);
create index idx_time on school_info (create_at);

# 课程信息
create table course_info
(
    course_id     bigint auto_increment
        primary key,
    course_name   varchar(30) not null,
    course_avatar varchar(255) default null,
    course_desc   int         not null,
    status int         not null,
    school_id    bigint      not null,
    create_at  BIGINT  not null comment '秒级',
    deleted    tinyint(1) default 0         not null,
    constraint fk_school_id
        foreign key (school_id) REFERENCES school_info (school_id)
);

create index idx_course_id on course_info (course_id);
create index idx_time on course_info (create_at);

# 课程用户
create table course_user
(
    course_id bigint not null,
    user_id  bigint not null,
    deleted    tinyint(1) default 0         not null,
    primary key (course_id, user_id),
    constraint fk_course_id
        foreign key (course_id) references course_info (course_id),
    constraint fk_course_user_id
        foreign key (user_id) references user (username)
);

create index idx_course_user on course_user (course_id, user_id);

# 公司信息
create table company_info
(
    company_id      bigint auto_increment
        primary key,
    company_name    varchar(30) not null,
    company_avatar  varchar(255) default null,
    company_desc    int          not null,
    company_address text    ,
    create_at  BIGINT   not null comment '秒级',
    status  int          not null,
    deleted    tinyint(1) default 0         not null,
    constraint fk_company_dept_id
        foreign key (company_id) references user (dept_id)
)AUTO_INCREMENT = 200000;

create index idx_company_id on company_info (company_id);
create index idx_time on company_info (create_at);

# 公司用户
create table company_user
(
    company_id bigint not null,
    user_id    bigint not null,
    deleted    tinyint(1) default 0         not null,
    primary key (company_id, user_id),
    constraint fk_company_id
        foreign key (company_id) references company_info (company_id),
    constraint fk_company_user_id
        foreign key (user_id) references user (username)
);

create index idx_company_user on company_user (company_id, user_id);