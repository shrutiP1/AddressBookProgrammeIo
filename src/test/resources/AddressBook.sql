#Database,TABLES

create database AddressBook_db;
use AddressBook_db;
CREATE table AddressBook_db(
   firstName varchar(58) primary key,
   lastName varchar(256) not null,
   phone_no varchar(30),
   email_id varchar(60)
);
insert into AddressBook_db values('Shruti','Patil','9130416631','shrutipatil13798@gmail.com');
insert into AddressBook_db values('Dadu','Patil','89899989','Dassssss@gmail.com');
insert into AddressBook_db values('Rinku','Bhingare','30416631','rinkuBhingare@gmail.com');
insert into AddressBook_db values('Karan','Patel','91020202','karanPatel@gmail.com');
create table address(
     address varchar(250) not null,
     city varchar(50) not null,
     state varchar(60) not null,
     zip int,
     name varchar(256),
     foreign key(name) references AddressBook_db(firstname)
);
insert into address values('Mangalmurti Building','Pune','Maharshtra',40214,'Shruti');
insert into address values('KeyLight Building','Mumbai','Maharshtra',402105,'Dadu');
insert into address values('Office Bilidng','Goa','Goa',400005,'Shruti');
insert into address values('Building','Ahemdabad','Gujrat',302104,'Rinku');
insert into address values('Mangalmurti Building','Pune','Maharshtra',402104,'Karan');
SELECT*FROM address;

#UC6

select AddressBook_db.*,address.city from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name where city='Pune';
select AddressBook_db.*,address.city from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name where state='Maharshtra';

#UC7

select address.city,count(address.city) from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name where city='Mumbai';
select address.state,count(address.state) from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name where state='Maharshtra';

#UC8
select AddressBook_db.firstName from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name where city='Pune' order by AddressBook_db.firstName;

#UC10

select address.city,count(address.city) from AddressBook_db INNER JOIN address ON AddressBook_db.firstName=address.name group by address.city;


create table contact_db(
   id int auto_increment primary key,
   firstName varchar(58) not null,
   lastName varchar(256) not null,
   phone_no varchar(30),
   email_id varchar(60),
   address varchar(250) not null,
   city varchar(50) not null,
   state varchar(60) not null,
	zip int
);
SELECT *FROM contact_db;
Update contact_db set city='Mumbai' where firstName='Shruti';
select *from contact_db where start BETWEEN cast('2018-02-09'AS date) and cast('2020-03-04' as date);
select state,count(id) as count from contact_db group by state;
insert into contact_db(firstName,lastName,phone_no,email_id,address,city,state,zip) values('Karan','Patel','91020202','karanPatel@gmail.com','Building no-6','Ahemdbad','Gujarat','502104');

