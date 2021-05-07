create table if not exists Users (
     id serial primary key,
     login text not null,
     password text not null,
     token text not null,
     utc_timezone smallint check(utc_timezone >= 0 and utc_timezone < 24)
);

create table if not exists Houses (
    id serial primary key,
    name text not null,
    color text not null check(color in ('orange', 'blue', 'red', 'yellow', 'green')),
    user_id integer references Users(id)
);

create table if not exists Rooms (
    id serial primary key,
    name text not null,
    color text not null check(color in ('orange', 'blue', 'red', 'yellow', 'green')),
    house_id integer not null references Houses(id)
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
    room_id integer references Rooms(id),
    user_id integer not null references Users(id)
);

create table if not exists SensorData (
    id serial primary key,
    sensor_id integer references Sensors(id),
    value real not null,
    time timestamp not null
);

insert into Users (login, password, token, utc_timezone) values('Nek', 'drozd1337', 'abcabc', 7);
insert into Users (login, password, token, utc_timezone) values('Vlad', 'teamlead', 'abcabc1', 7);

drop table if exists SensorData;
drop table if exists Sensors;
drop table if exists SensorTypes;
drop table if exists Rooms;
drop table if exists Houses;
drop table if exists Users;