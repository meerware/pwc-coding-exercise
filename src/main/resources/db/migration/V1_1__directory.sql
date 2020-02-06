-- Tables
-- Tables: Directory
create table directory (
    id varchar(36) not null,
    created timestamp,
    modified timestamp,
    "name" varchar(64),
    creator_id varchar(36),
    modifier_id varchar(36),
    primary key (id));

-- Tables: Directory Contact
create table directory_contact (
    directory_id varchar(36) not null,
    country varchar(2),
    locality varchar(128),
    postcode varchar(16),
    state varchar(128),
    email varchar(384),
    "name" varchar(128),
    "lines" varchar(512),
    phone varchar(32));

-- Foreign Keys: Directory
alter table directory add constraint directory_creator_foreign_key foreign key (creator_id) references actor (id);
alter table directory add constraint directory_modifier_foreign_key foreign key (modifier_id) references actor (id);

-- Foreign Keys: Directory Contact
alter table directory_contact add constraint directory_contact_foreign_key foreign key (directory_id) references directory (id);