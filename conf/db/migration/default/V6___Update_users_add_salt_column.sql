alter table users
add column salt VARCHAR(20) null;

update users set salt = '' where salt is null;

alter table users
alter column salt set not null;