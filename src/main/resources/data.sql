
-- TEST_USER
insert into H3_USER (id, email, name, role,dtype) values (1 ,'halucinor0@gmail.com','first', 'ADMIN', 'UserModel');
insert into H3_USER (id, email, name, role,dtype) values (2 ,'sjbaek@gabia.com','second', 'USER', 'UserModel');
insert into H3_USER (id, email, name, role,dtype) values (3 ,'test@gmail.com','third', 'USER', 'UserModel');
-- TEST_AGENDA

insert into AGENDA (id, description, start_datetime, end_datetime, max_vote, status, type) values (1, 'test agenda1', '2023-01-10T00:00:00','2023-01-10T00:30:00', 100000, 0, 0);
insert into AGENDA (id, description, start_datetime, end_datetime, max_vote, status, type) values (2, 'test agenda2', '2023-01-10T01:00:00','2023-01-10T01:30:00', 100000, 1, 1);
insert into AGENDA (id, description, start_datetime, end_datetime, max_vote, status, type) values (3, 'test agenda3', '2023-01-10T02:00:00','2023-01-10T02:30:00', 100000, 2, 0);

-- TEST_VOTE

