DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password ) VALUES
  ('User', 'user@yandex.ru', '{noop}password'),
  ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Metropol'),
       ('Ivanyich'),
       ('U berez'),
       ('Koryushka'),
       ('MyLove'),
       ( 'Mr Jones'),
       ('Leshenko'),
       ('Absolut');

INSERT INTO dishes (name, price, restaurant_id) VALUES

     ('Borsh', 100, 100002),
     ('Soup', 80, 100003),
     ('Lamb', 130, 100004),
     ('Salmon', 150, 100005),
     ('Salmon', 200, 100006),
     ('Salad', 70, 100007),
     ('Ice-cream', 50, 100008),
     ('Roast', 160, 100009),
     ('Ham', 100, 100002);
