# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

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




# --- !Downs

drop table if exists users cascade;

