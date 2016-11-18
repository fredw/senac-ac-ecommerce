insert into user (`name`, email, password) values ('Administrador', 'admin@ddf.com', '12345');

-- Temporary products
INSERT INTO product (`name`, `description`, `price`, `featured`, `image`) VALUES (
  'Produto ABC',
  'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla auctor turpis ac nisi iaculis lobortis.',
   435.45,
  1,
  'image.jpg'
);
INSERT INTO product (`name`, `description`, `price`, `featured`, `image`) VALUES (
  'Produto XYZ',
  'Praesent in aliquam leo, sit amet luctus eros. In hac habitasse platea dictumst.',
   983.90,
  0,
  'image.jpg'
);