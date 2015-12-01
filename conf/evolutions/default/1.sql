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
  tools_tool_id             bigint,
  duration                  timestamp,
  constraint pk_borrowed primary key (id))
;

create table comments (
  comment_id                bigserial not null,
  user_user_id              bigint,
  tool_tool_id              bigint,
  body                      varchar(255),
  datetime_posted           timestamp,
  constraint pk_comments primary key (comment_id))
;

create table categories (
  cat_id                    bigserial not null,
  cat_name                  varchar(255),
  constraint pk_categories primary key (cat_id))
;

create table tools (
  tool_id                   bigserial not null,
  tool_name                 varchar(255),
  tool_desc                 varchar(255),
  tool_type_cat_id          bigint,
  tool_owner_user_id        bigint,
  borough_bor_id            integer,
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
  constraint pk_users primary key (user_id))
;

alter table borrowed add constraint fk_borrowed_users_1 foreign key (users_user_id) references users (user_id);
create index ix_borrowed_users_1 on borrowed (users_user_id);
alter table borrowed add constraint fk_borrowed_tools_2 foreign key (tools_tool_id) references tools (tool_id);
create index ix_borrowed_tools_2 on borrowed (tools_tool_id);
alter table comments add constraint fk_comments_user_3 foreign key (user_user_id) references users (user_id);
create index ix_comments_user_3 on comments (user_user_id);
alter table comments add constraint fk_comments_tool_4 foreign key (tool_tool_id) references tools (tool_id);
create index ix_comments_tool_4 on comments (tool_tool_id);
alter table tools add constraint fk_tools_tool_type_5 foreign key (tool_type_cat_id) references categories (cat_id);
create index ix_tools_tool_type_5 on tools (tool_type_cat_id);
alter table tools add constraint fk_tools_tool_owner_6 foreign key (tool_owner_user_id) references users (user_id);
create index ix_tools_tool_owner_6 on tools (tool_owner_user_id);
alter table tools add constraint fk_tools_borough_7 foreign key (borough_bor_id) references boroughs (bor_id);
create index ix_tools_borough_7 on tools (borough_bor_id);



# --- !Downs

drop table if exists boroughs cascade;

drop table if exists borrowed cascade;

drop table if exists comments cascade;

drop table if exists categories cascade;

drop table if exists tools cascade;

drop table if exists users cascade;

