insert into user(id, name, password) values(1, 'admin', 'adminPassword');
insert into user(id, name, password) values(2, 'librarian', 'libPassword');
insert into user(id, name, password) values(3, 'co-librarian', 'coPassword');
insert into user(id, name, password) values(4, 'customer', 'password');

insert into user_roles(user_id, roles) values(1, 0);
insert into user_roles(user_id, roles) values(2, 1);
insert into user_roles(user_id, roles) values(3, 2);
insert into user_roles(user_id, roles) values(4, 3);