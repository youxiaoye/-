create table usertab (
card_id varchar(20) primary key,
name varchar(10) not null,
sex varchar(10) not null
);
create table control (
card_id varchar(20) not null,
constraint cucard_id foreign key (card_id) references usertab(card_id),
typee varchar(10) not null
);
insert into usertab(card_id,name,sex)
values ('12300x','张三','male'),
       ('12333x','李四','male'),
       ('10086y','小美','female'),
       ('00001x','小猪','male');
insert into control(card_id,typee)
values ('12300x','ordinary'),
       ('12333x','ordinary'),
       ('10086y','ordinary'),
       ('00001x','control');
select * from usertab;
insert into usertab(card_id,name,sex)
values('111111','test','cat');