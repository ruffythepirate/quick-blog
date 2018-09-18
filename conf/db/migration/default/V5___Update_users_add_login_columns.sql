alter table users
add column password varchar(200) null,
add column last_login timestamp null;

update users set password = 'incognito' where password is null;

alter table users
alter column password set not null;