drop table employee_master;
create table employee_master
(
    user_Id varchar(30) not null primary key,
    name varchar(60) not null,
    manager_name varchar(60) not null,
    region varchar(10) not null,
    imageCount number,
    bottleCount number,
    happyCount number,
    angryCount number,
    neutralCount number,
    sadCount number
);

ALTER TABLE employee_master
    ADD CONSTRAINT ck_region CHECK (region IN ('North','South','East','West'));
    
insert into employee_master
(user_Id, name, manager_name, region, imageCount, bottleCount, happyCount, angryCount, neutralCount, sadCount) values
('VidyutV', 'Vidyut Verma', 'Rajesh Ramdas', 'North', 20, 10, 10, 5, 2, 3);

insert into employee_master
(user_Id, name, manager_name, region, imageCount, bottleCount, happyCount, angryCount, neutralCount, sadCount) values
('KallolC', 'Kallol Chaudhuri', 'Rajesh Ramdas', 'East', 30, 1, 10, 10, 5, 5);

select * from employee_master;

drop table processed_data;
create table processed_data
(
    event_Id varchar(30) not null primary key,
    user_Id varchar(30) not null,
    person_Emotion number,
    person_Holding_Bottle char(1),
    imageFileName varchar(60),
    FOREIGN KEY (user_Id) REFERENCES employee_master(user_Id)
);

ALTER TABLE processed_data
    ADD CONSTRAINT ck_person_Holding_Bottle CHECK (person_Holding_Bottle IN ('Y','N'));
    
insert into processed_data
(event_Id, user_Id, person_Emotion, person_Holding_Bottle, imageFileName) values
('E001', 'KallolC', 1, 'N', 'abcd.jpg');

insert into processed_data
(event_Id, user_Id, person_Emotion, person_Holding_Bottle, imageFileName) values
('E002', 'VidyutV', 1, 'Y', 'abcd.jpg');

select * from processed_data;