create TABLE users (
  id VARCHAR(64) PRIMARY KEY ,
  name VARCHAR(64),
  password VARCHAR(64),
  authtoken INTEGER
);

insert into users (id, name, password, authtoken) values ('1001', 'rory', 'fishcakes', 73831563);
insert into users (id, name, password, authtoken) values ('1002', 'talula', 'granola', 10749277);