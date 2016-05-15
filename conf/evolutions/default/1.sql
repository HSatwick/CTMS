# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table boroughs (
  bor_id                    serial not null,
  bor_name                  varchar(255),
  constraint pk_boroughs primary key (bor_id))
;

create table borrowed (
  id                        serial not null,
  users_user_id             bigint,
  tools_tool_id             bigint,
  active                    integer,
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

create table passwordresetrequest (
  pass_id                   serial not null,
  user_email                varchar(255),
  datetime_requested        timestamp,
  token                     bigint,
  constraint pk_passwordresetrequest primary key (pass_id))
;

create table request_tool (
  request_tool_id           bigserial not null,
  user_user_id              bigint,
  owner_user_id             bigint,
  tools_tool_id             bigint,
  status                    integer,
  constraint pk_request_tool primary key (request_tool_id))
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
  tool_borrower_user_id     bigint,
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

create table votesTools (
  vote_id                   bigserial not null,
  user_user_id              bigint,
  tools_tool_id             bigint,
  vote                      bigint,
  constraint pk_votesTools primary key (vote_id))
;

alter table borrowed add constraint fk_borrowed_users_1 foreign key (users_user_id) references users (user_id);
create index ix_borrowed_users_1 on borrowed (users_user_id);
alter table borrowed add constraint fk_borrowed_tools_2 foreign key (tools_tool_id) references tools (tool_id);
create index ix_borrowed_tools_2 on borrowed (tools_tool_id);
alter table comments add constraint fk_comments_user_3 foreign key (user_user_id) references users (user_id);
create index ix_comments_user_3 on comments (user_user_id);
alter table comments add constraint fk_comments_tool_4 foreign key (tool_tool_id) references tools (tool_id);
create index ix_comments_tool_4 on comments (tool_tool_id);
alter table request_tool add constraint fk_request_tool_user_5 foreign key (user_user_id) references users (user_id);
create index ix_request_tool_user_5 on request_tool (user_user_id);
alter table request_tool add constraint fk_request_tool_owner_6 foreign key (owner_user_id) references users (user_id);
create index ix_request_tool_owner_6 on request_tool (owner_user_id);
alter table request_tool add constraint fk_request_tool_tools_7 foreign key (tools_tool_id) references tools (tool_id);
create index ix_request_tool_tools_7 on request_tool (tools_tool_id);
alter table tools add constraint fk_tools_tool_type_8 foreign key (tool_type_cat_id) references categories (cat_id);
create index ix_tools_tool_type_8 on tools (tool_type_cat_id);
alter table tools add constraint fk_tools_tool_owner_9 foreign key (tool_owner_user_id) references users (user_id);
create index ix_tools_tool_owner_9 on tools (tool_owner_user_id);
alter table tools add constraint fk_tools_borough_10 foreign key (borough_bor_id) references boroughs (bor_id);
create index ix_tools_borough_10 on tools (borough_bor_id);
alter table tools add constraint fk_tools_tool_borrower_11 foreign key (tool_borrower_user_id) references users (user_id);
create index ix_tools_tool_borrower_11 on tools (tool_borrower_user_id);
alter table votesTools add constraint fk_votesTools_user_12 foreign key (user_user_id) references users (user_id);
create index ix_votesTools_user_12 on votesTools (user_user_id);
alter table votesTools add constraint fk_votesTools_tools_13 foreign key (tools_tool_id) references tools (tool_id);
create index ix_votesTools_tools_13 on votesTools (tools_tool_id);



# --- !Downs

drop table if exists boroughs cascade;

drop table if exists borrowed cascade;

drop table if exists comments cascade;

drop table if exists passwordresetrequest cascade;

drop table if exists request_tool cascade;

drop table if exists categories cascade;

drop table if exists tools cascade;

drop table if exists users cascade;

drop table if exists votesTools cascade;

