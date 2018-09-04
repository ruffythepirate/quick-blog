create table articles (
  id serial PRIMARY KEY,
  title varchar(200) not null,
  text text not null,
  updated TIMESTAMP not null,
  created TIMESTAMP not null
)

