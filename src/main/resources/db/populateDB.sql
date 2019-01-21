DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password ) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO restaurants (name)
VALUES ('Metropol'),
       ('Ivanyich'),
       ('U berez'),
       ('Koryushka'),
       ('MyLove');

INSERT INTO dishes (name, price, restaurant_id) VALUES

     ('Borsch', 80, 100002),
     ('Solyanka', 120, 100003),
     ('Pork', 160, 100004),
     ('Roast', 170, 100005),
     ('Eggs', 90, 100006),
     ('Salad', 70, 100002),
     ('Ice-cream', 60, 100004),
     ('Chicken-soup', 90, 100003),
     ('Salmon', 200, 100002),
     ('Wok', 180, 100002),
     ('Pizza', 150, 100002);

INSERT INTO votes(USER_ID, RESTAURANT_ID) VALUES
    (100000, 100002),
     (100001, 100005);