desc guestbook

insert into guestbook values(null,'안대혁','1234','테스트입니다',now());

select no,name,reg_date,contents from guestbook order by reg_date desc;

delete from guestbook where no=1 and password='1234';