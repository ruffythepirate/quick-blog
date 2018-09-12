create table users (
  id serial PRIMARY KEY,
  name varchar(100) not null,
  email varchar(100) not null,
  updated timestamp not null default now(),
  created timestamp not null default now()
);

alter table articles
  add column fk_user integer references articles(id) on delete restrict;

insert into users(name, email)
values('admin', 'admin@quick.com');

update articles set fk_user = lastval();
