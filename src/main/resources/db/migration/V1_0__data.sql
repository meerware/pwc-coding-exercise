-- Tables
-- Tables: Actor
create table actor (
  type varchar(16) not null,
  id varchar(36) not null,
  created timestamp,
  modified timestamp,
  creator_id varchar(36),
  modifier_id varchar(36),
  primary key (id));
  
-- Foreign Keys: Actor
alter table actor add constraint actor_creator_foreign_key foreign key (creator_id) references actor (id);
alter table actor add constraint actor_modifier_foreign_key foreign key (modifier_id) references actor (id);
