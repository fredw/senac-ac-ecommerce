insert into role (`name`) values ('Administrator');
insert into role (`name`) values ('Customer');

insert into user (`name`, email, password, role_id) values ('Administrador', 'admin@ddf.com', '12345', 1);

INSERT INTO product (`code`, `name`, `description`, `price`, `featured`, `image`, `file`) VALUES ('123', 'Produto ABC', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla auctor turpis ac nisi iaculis lobortis.', 435.45, 1, 'image.jpg', 'file.pdf');
INSERT INTO product (`code`, `name`, `description`, `price`, `featured`, `image`, `file`) VALUES ('XYZ', 'Produto XYZ', 'Praesent in aliquam leo, sit amet luctus eros. In hac habitasse platea dictumst.', 983.90, 0, 'image.jpg', 'file.pdf');