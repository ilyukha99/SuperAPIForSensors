create table if not exists Users (
    id serial primary key,
    login text not null unique,
    password text not null,
    token text not null,
    utc_timezone smallint check(utc_timezone >= 0 and utc_timezone < 24)
);

create table if not exists SensorTypes (
    id serial primary key,
    name text not null,
    description text
);

create table if not exists Sensors (
    id serial primary key,
    name text not null,
    type_id integer not null references SensorTypes(id),
    user_id integer not null references Users(id)
);

create table if not exists SensorData (
    id serial primary key,
    sensor_id integer references Sensors(id),
    value real not null,
    time timestamp not null
);

alter table users add constraint login_uniqueness unique (login);

insert into Users (login, password, token, utc_timezone) values('Nek', 'drozd1337', 'abcabc', 7);
insert into Users (login, password, token, utc_timezone) values('Vlad', 'teamlead', 'abcabc1', 7);

insert into SensorTypes (name, description) values('illumination', 'lux');
insert into SensorTypes (name, description) values('humidity', '%');
insert into SensorTypes (name, description) values('temperature', 'degrees celsius');
insert into SensorTypes (name, description) values('pressure', 'mm of mercury');

insert into Sensors (name, type_id, user_id) values('is1', 1, 2);
insert into Sensors (name, type_id, user_id) values('hs1', 2, 2);
insert into Sensors (name, type_id, user_id) values('hs2', 2, 2);
insert into Sensors (name, type_id, user_id) values('ts1', 3, 2);
insert into Sensors (name, type_id, user_id) values('ts2', 3, 2);
insert into Sensors (name, type_id, user_id) values('ps1', 4, 2);
insert into Sensors (name, type_id, user_id) values('ps2', 4, 2);

insert into SensorData (sensor_id, value, time) values(1, 769, '1999-01-08 04:05:01');
insert into SensorData (sensor_id, value, time) values(4, 19.5, '1999-01-08 04:05:06');
insert into SensorData (sensor_id, value, time) values(4, 19.3, '1999-01-08 04:05:36');
insert into SensorData (sensor_id, value, time) values(4, 19.4, '1999-01-08 04:06:06');
insert into SensorData (sensor_id, value, time) values(1, 768, '1999-01-08 04:06:11');
insert into SensorData (sensor_id, value, time) values(4, 19.7, '1999-01-08 04:06:36');
insert into SensorData (sensor_id, value, time) values(4, 19.9, '1999-01-08 04:07:06');
insert into SensorData (sensor_id, value, time) values(4, 19.8, '1999-01-08 04:07:21');
insert into SensorData (sensor_id, value, time) values(4, 19.6, '1999-01-08 04:07:36');
insert into SensorData (sensor_id, value, time) values(4, 19.6, '1999-01-08 04:08:06');
insert into SensorData (sensor_id, value, time) values(1, 766, '1999-01-08 04:08:31');
insert into SensorData (sensor_id, value, time) values(4, 19.5, '1999-01-08 04:08:36');
insert into SensorData (sensor_id, value, time) values(1, 765, '1999-01-08 04:09:41');

drop table if exists SensorData;
drop table if exists Sensors;
drop table if exists SensorTypes;
drop table if exists Users;