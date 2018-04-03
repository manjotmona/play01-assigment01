# --- !Ups
 create table user_profile (fname varchar(20) not null, mname varchar(20), lname varchar(20), username varchar(20), password varchar(20), confirmPassword varchar(20), mobile varchar(20),  gender varchar(10), age int, hobby varchar(10), isAdmin boolean DEFAULT false,isEnabled boolean Default true);
 create table assignment_table(id int Primary key auto_increment, title varchar(20), description varchar(50));

# --- !Downs
drop table user_profile;
drop table assignment_table;