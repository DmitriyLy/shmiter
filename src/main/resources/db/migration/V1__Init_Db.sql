create sequence activation_token_seq start with 1 increment by 50;
create sequence message_seq start with 1 increment by 50;
create sequence usr_seq start with 1 increment by 50;

create table activation_token
(
    id            bigint not null,
    creation_date timestamp(6),
    token         varchar(255),
    usr_id        bigint,
    primary key (id)
);

create table message
(
    id      bigint not null,
    file    varchar(255),
    message varchar(2048),
    tag     varchar(255),
    user_id bigint,
    primary key (id)
);

create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
);

create table usr
(
    id       bigint  not null,
    active   boolean not null,
    password varchar(255),
    username varchar(255),
    email varchar(255),
    primary key (id)
);


alter table if exists activation_token
    add constraint activation_token_fk_usr
    foreign key (usr_id) references usr;

alter table if exists message
    add constraint message_fk_usr
    foreign key (user_id) references usr;

alter table if exists user_role
    add constraint user_role_fr_usr
    foreign key (user_id) references usr;