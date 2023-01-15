
-- TEST_USER
insert into H3_USER (id, email, role, vote_count,dtype, password) values (100 ,'halucinor0@gmail.com', 'ADMIN',100, 'UserModel',' ');
insert into H3_USER (id, email,  role, vote_count,dtype,password) values (101 ,'sjbaek@gabia.com', 'USER',200, 'UserModel',' ');
insert into H3_USER (id, email,  role, vote_count,dtype,password) values (102 ,'test@gmail.com', 'USER',300, 'UserModel',' ');
-- TEST_AGENDA

insert into AGENDA (id, description, start_date_time, end_date_time, max_vote, status, type) values (100, 'test agenda1', '2023-01-10T00:00:00','2023-01-10T00:30:00', 100, 0, 0);
insert into AGENDA (id, description, start_date_time, end_date_time, max_vote, status, type) values (101, 'test agenda2', '2023-01-10T01:00:00','2023-01-10T01:30:00', 200, 1, 1);
insert into AGENDA (id, description, start_date_time, end_date_time, max_vote, status, type) values (102, 'test agenda3', '2023-01-10T02:00:00','2023-01-10T02:30:00', 300, 2, 0);

-- TEST_VOTE


insert into VOTE (id, user_id, agenda_id, type, count, voting_date_time) values (100, 100, 100, 0, 10,'2023-01-10T00:30:00');
insert into VOTE (id, user_id, agenda_id, type, count, voting_date_time) values (101, 101, 100, 1, 20,'2023-01-10T01:30:00');
insert into VOTE (id, user_id, agenda_id, type, count, voting_date_time) values (102, 102, 100, 2, 30,'2023-01-10T02:30:00');


