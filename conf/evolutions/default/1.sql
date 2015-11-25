# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table boroughs (
  bor_id                    serial not null,
  bor_name                  varchar(255),
  constraint pk_boroughs primary key (bor_id))
;

create table borrowed (
  id                        varchar(255) not null,
  users_user_id             bigint,
  tools_tool_id             varchar(255),
  duration                  timestamp,
  constraint pk_borrowed primary key (id))
;

create table comments (
  comment_id                varchar(255) not null,
  constraint pk_comments primary key (comment_id))
;

create table categories (
  cat_id                    varchar(255) not null,
  cat_name                  varchar(255),
  constraint pk_categories primary key (cat_id))
;

create table tools (
  tool_id                   varchar(255) not null,
  tool_name                 varchar(255),
  tool_desc                 varchar(255),
  tool_type_cat_id          varchar(255),
  tool_owner_user_id        bigint,
  available                 integer,
  constraint pk_tools primary key (tool_id))
;

create table users (
  user_id                   bigserial not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email_adrs                varchar(255),
  password                  varchar(255),
  constraint uq_users_email_adrs unique (email_adrs),
  constraint uq_users_password unique (password),
  constraint pk_users primary key (user_id))
;

alter table borrowed add constraint fk_borrowed_users_1 foreign key (users_user_id) references users (user_id);
create index ix_borrowed_users_1 on borrowed (users_user_id);
alter table borrowed add constraint fk_borrowed_tools_2 foreign key (tools_tool_id) references tools (tool_id);
create index ix_borrowed_tools_2 on borrowed (tools_tool_id);
alter table tools add constraint fk_tools_tool_type_3 foreign key (tool_type_cat_id) references categories (cat_id);
create index ix_tools_tool_type_3 on tools (tool_type_cat_id);
alter table tools add constraint fk_tools_tool_owner_4 foreign key (tool_owner_user_id) references users (user_id);
create index ix_tools_tool_owner_4 on tools (tool_owner_user_id);



# --- !Downs

drop table if exists boroughs cascade;

drop table if exists borrowed cascade;

drop table if exists comments cascade;

drop table if exists categories cascade;

drop table if exists tools cascade;

drop table if exists users cascade;

