alter table articles
add column updated TIMESTAMP not null default now(),
add column created TIMESTAMP not null default now();

update articles set updated = now() where updated is null;
update articles set created = now() where created is null;

alter table articles
alter column updated set not null,
alter column created set not null;

