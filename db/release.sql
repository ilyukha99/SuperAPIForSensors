create table if not exists Users (
     id serial primary key,
     login text not null unique,
     password text not null,
     token text not null,
     sensor_token text not null,
     utc_timezone smallint check(utc_timezone >= 0 and utc_timezone < 24)
);

create table if not exists Houses (
    id serial primary key,
    name text not null,
    color text not null check(color in ('orange', 'blue', 'red', 'yellow', 'green')),
    user_id integer references Users(id) on delete cascade
);

create table if not exists Rooms (
    id serial primary key,
    name text not null,
    color text not null check(color in ('orange', 'blue', 'red', 'yellow', 'green')),
    house_id integer not null references Houses(id) on delete cascade
);

create table if not exists SensorTypes (
    id serial primary key,
    name text not null,
    description text
);

create table if not exists Sensors (
    id serial primary key,
    name text not null,
    type_id integer not null references SensorTypes(id) on delete restrict ,
    room_id integer references Rooms(id) on delete cascade,
    user_id integer not null references Users(id) on delete cascade
);

create table if not exists SensorData (
    id serial primary key,
    sensor_id integer references Sensors(id) on delete cascade,
    value real not null,
    time timestamp not null
);

-- insert into Users (login, password, token, sensor_token, utc_timezone) values('Nek', 'drozd1337', 'abcabc1', 'fsdfdsskskskd', 7);
-- insert into Users (login, password, token, sensor_token, utc_timezone) values('Vlad', 'teamlead', 'abcabc2', 'fddsdsdssdsd', 7);

insert into Houses (name, color, user_id) values('first one', 'red', 2);
insert into Houses (name, color, user_id) values('second one', 'green', 2);

insert into Rooms (name, color, house_id) values('initial', 'yellow', 2);
insert into Rooms (name, color, house_id) values('additional', 'green', 2);

insert into SensorTypes (name, description) values('illumination', 'lux');
insert into SensorTypes (name, description) values('humidity', '%');
insert into SensorTypes (name, description) values('temperature', 'degrees celsius');
insert into SensorTypes (name, description) values('pressure', 'mm of mercury');

insert into Sensors (name, type_id, room_id, user_id) values('is1', 1, 2, 2);
insert into Sensors (name, type_id, room_id, user_id) values('hs1', 2, 2, 2);
insert into Sensors (name, type_id, room_id, user_id) values('hs2', 2, 2, 2);
insert into Sensors (name, type_id, room_id, user_id) values('ts1', 3, 1, 2);
insert into Sensors (name, type_id, room_id, user_id) values('ts2', 3, 1, 2);
insert into Sensors (name, type_id, room_id, user_id) values('ps1', 4, 1, 2);
insert into Sensors (name, type_id, room_id, user_id) values('ps2', 4, 1, 2);

insert into SensorData (sensor_id, value, time) values(7, 769, '1999-01-08 04:05:01');
insert into SensorData (sensor_id, value, time) values(2, 19.5, '1999-01-08 04:05:06');
insert into SensorData (sensor_id, value, time) values(2, 19.3, '1999-01-08 04:05:36');
insert into SensorData (sensor_id, value, time) values(2, 19.4, '1999-01-08 04:06:06');
insert into SensorData (sensor_id, value, time) values(7, 768, '1999-01-08 04:06:11');
insert into SensorData (sensor_id, value, time) values(2, 19.7, '1999-01-08 04:06:36');
insert into SensorData (sensor_id, value, time) values(2, 19.9, '1999-01-08 04:07:06');
insert into SensorData (sensor_id, value, time) values(2, 19.8, '1999-01-08 04:07:21');
insert into SensorData (sensor_id, value, time) values(2, 19.6, '1999-01-08 04:07:36');
insert into SensorData (sensor_id, value, time) values(2, 19.6, '1999-01-08 04:08:06');
insert into SensorData (sensor_id, value, time) values(7, 766, '1999-01-08 04:08:31');
insert into SensorData (sensor_id, value, time) values(2, 19.5, '1999-01-08 04:08:36');
insert into SensorData (sensor_id, value, time) values(7, 765, '1999-01-08 04:09:41');

drop table if exists SensorData;
drop table if exists Sensors;
drop table if exists SensorTypes;
drop table if exists Rooms;
drop table if exists Houses;
-- drop table if exists Users;