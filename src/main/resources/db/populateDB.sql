DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
DELETE FROM votes;
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

INSERT INTO dishes (name, price, restaurant_id, ENABLED) VALUES

     ('Borsch', 80, 100002, true),
     ('Solyanka', 120, 100003, true),
     ('Pork', 160, 100004, false),
     ('Roast', 170, 100005, false),
     ('Eggs', 90, 100006, true),
     ('Salad', 70, 100002, false),
     ('Ice-cream', 60, 100004, true),
     ('Chicken-soup', 90, 100003, true),
     ('Salmon', 200, 100002,true ),
     ('Wok', 180, 100002, true ),
     ('Pizza', 150, 100002,false);

INSERT INTO votes(USER_ID, RESTAURANT_ID) VALUES
    (100000, 100002),
     (100001, 100005);
