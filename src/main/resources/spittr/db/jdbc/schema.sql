drop table if exists Spitters;

create table Spitters (
  id int not null auto_increment,
  firstName varchar(255) not null,
  lastName varchar(255) not null,
  username varchar(255) not null,
  password varchar(25) not null,
  email varchar(50) not null,
  primary key (id)
);


 create table Spittles (
   spittle_id int not null auto_increment,
   id int not null,
   message varchar(2000) not null,
   time datetime not null,
   latitude double not null,
   longitude double not null,
   primary key (spittle_id),
   foreign key (id) references spitters(id)
 );