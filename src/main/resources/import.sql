insert into role (`name`) values ('Administrator');
insert into role (`name`) values ('Customer');

-- Password: 12345
insert into user (`name`, email, password, role_id, enabled) values ('Administrador', 'fred.wuerges+admin@gmail.com', '$2a$10$yXlnqwVRadcEEQ3c4Z5qmOT2pyavdw0seSopNQrmsFls3qXrd4cra', 1, 1);
insert into user (`name`, email, password, role_id, enabled) values ('Cliente teste', 'fred.wuerges+cliente@gmail.com', '$2a$10$yXlnqwVRadcEEQ3c4Z5qmOT2pyavdw0seSopNQrmsFls3qXrd4cra', 2, 1);

insert into customer (`user_id`) values (2);

insert into order_status (`name`) values ('Aguardando pagamento');
insert into order_status (`name`) values ('Pagamento confirmado');
insert into order_status (`name`) values ('Cancelado');

insert into cart_status (`name`) values ('Ativo');
insert into cart_status (`name`) values ('Comprado');