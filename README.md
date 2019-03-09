# VotingApi
Api for voting system where to have a dinner
Api allows to store in database a list of restaurants with menu that are edited by admin. User can vote for the restaurant once a day where one wants to have a dinner this day.Additonally, the log of user votes and dishes in the restaurant is provided. 

Api provides functionality of adding and editing restaurants and it's menu by the admin. Menu is represented by menu items Dish. 
Each dish has name, price, date created and boolean flag "enabled" that may be used to denote whether this dish is shown in current day menu or in the log of dishes only.
Admin can add restaurants (without menu), delete restaurants (with all it's dishes), or get all restaurants with dishes or one particular restaurant.
Also, admin can add dishes to the restaurant, delete dishes, update dishes or get particular dish as well as all dishes of specified restaurant.
Note, that each restaurant keep the log of added dishes. Use DELETE /rest/admin/restaurants/{restaurantId}/dishes/{dishId} request to delete dish irretrieveably or PUT /rest/admin/restaurants/{restaurantId}/dishes/{dishId}/enabled to mark the dish as deleted from actual menu.
In latter case, in will be shown in the dish log only. Similarly, use GET  /rest/admin/restaurants/{restaurantId}/dishes/enabled to get all dishes to be shown in menu, or
GET /rest/admin/restaurants/{restaurantId}/dishes to get dish log of the restaurant. Note that being updated by PUT /rest/admin/restaurants/{restaurantId}/dishes/{dishId},
dish is automatically assigned the current date.
Additionally, admin can manage regular users and delete all users votes on specified date.
User can manage his profile and vote for one  restaurant once a day.One can change his mind and vote for another restaurant by 11-00 server time each day. 
Of course to be able to vote, user can get list restaurants with menu as well as one particular restaurant. Additionally, user can his own vote log as well as vote log of particular restaurant. The latter is provided as Map<Map<LocalDate, Integer>, 
indicating how much users gave their votes for this restaurant on a particular date.

Here are curls with brief description:

-register new user:

curl -X POST \
  http://localhost:8080/VotingApi/rest/profile/register \
  -H 'Authorization: Basic Og==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 4c921961-59a3-44f5-b824-501f6bf9fe58' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": null,
    "name": "Lukshev",
    "email": "userlukashev@yandex.ru",
    "password": "3844s"
}'

-update authentificated user's profile:

curl -X PUT \
  http://localhost:8080/VotingApi/rest/profile \
  -H 'Authorization: Basic dXNlcmx1a2FzaGV2QHlhbmRleC5ydTozODQ0cw==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d20cb174-da97-4325-88d4-7b4ff44d3cd2' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": 100020,
    "name": "Lukshev13",
    "email": "userlukashev@yandex.ru",
    "password": "3844s"
}'

-get authentificated user's profile:

curl -X GET \
  http://localhost:8080/VotingApi/rest/profile \
  -H 'Authorization: Basic dXNlcmx1a2FzaGV2QHlhbmRleC5ydTozODQ0cw==' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 9e00f319-ba51-4452-9138-7558934554aa' \
  -H 'cache-control: no-cache' 
  
  
-delete authentificated user's profile:

curl -X DELETE \
  http://localhost:8080/VotingApi/rest/profile \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: b4484ad9-581a-4a0e-94c4-8565881fd0c0' \
  -H 'cache-control: no-cache' 

-user gets all restaurants:

curl -X GET \
  http://localhost:8080/VotingApi/rest/user/restaurants \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5ba0c887-ebc8-4528-ab83-c5f94da364ee' \
  -H 'cache-control: no-cache'
  
  -user get restaurant by id:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/user/restaurants/100002 \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a425077c-1d14-40aa-85cc-38e966499e6d' \
  -H 'cache-control: no-cache'
  
  - user votes for the restaurant by id:
  
  curl -X PUT \
  http://localhost:8080/VotingApi/rest/user/votes/100002 \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 54a88db8-37ef-4657-bb09-d480c6cfd611' \
  -H 'cache-control: no-cache'
  
  -user gets his vote log:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/user/myvotes \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: d6e33686-7860-4338-9a55-61f9bfa1f442' \
  -H 'cache-control: no-cache'
  
  -user gets vote log for the restaurant:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/user/log/100002 \
  -H 'Authorization: Basic dXNlckB5YW5kZXgucnU6cGFzc3dvcmQ=' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 9464f72c-3ac8-456e-aa50-fcb34794e7a0' \
  -H 'cache-control: no-cache'
  
  -admin deletes votes on certain date:
  
  curl -X DELETE \
  'http://localhost:8080/VotingApi/rest/admin/votes?date=2019-03-09' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 1cc4ddda-7211-46cf-bda9-1465066f6b24' \
  -H 'cache-control: no-cache'
  
  -admin gets all restaurants:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/restaurants \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 224b186c-c677-478b-814f-6d86a8c96705' \
  -H 'cache-control: no-cache'
  
  -admin gets restaurant by id:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 9cfc022a-ba19-403f-acca-cd871741bbc3' \
  -H 'cache-control: no-cache'
  
  -admin adds restaurant:
  
  curl -X POST \
  http://localhost:8080/VotingApi/rest/admin/restaurants \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 099759ce-6814-4525-afb8-29e92cb6e224' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": null,
    "name": "Big apple"
}'

-admin deletes restaurant:

curl -X DELETE \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100021 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5bd89a31-e4ce-41bd-a9ff-b903cc3ea43e' \
  -H 'cache-control: no-cache'
  
  -admin gets all dishes of the restaurant:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: f174fedd-0a25-455d-9249-4a324feba090' \
  -H 'cache-control: no-cache'

-admin gets dish of the restaurant by id:

curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes/100012 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: f8b9ef73-756b-4bd3-8d96-5f77898e0e18' \
  -H 'cache-control: no-cache'
  
  -admin creates dish:
  
  curl -X POST \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 51647bfd-d9b8-462b-b05b-5bc9a955ff13' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": null,
    "name": "Shrimp",
    "price": 70
}'

 -admin updates dish:
curl -X PUT \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes/100022 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 9b2019aa-2aa2-480e-81c2-9f5959b94dce' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": 100022,
    "name": "Shrimp12",
    "price": 230
}'

-admin deletes dish:

curl -X DELETE \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes/100022 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5538c5ae-d4e0-4dbd-a69c-31123ed44f8a' \
  -H 'cache-control: no-cache' 
  
  -admin gets all enabled dishes of the restaurant:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes/enabled \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 44562e92-e9f6-4079-8b13-84a6b50765cb' \
  -H 'cache-control: no-cache' 
  
  -admin sets enabled parameter of dish:
  
  curl -X PUT \
  'http://localhost:8080/VotingApi/rest/admin/restaurants/100002/dishes/100012/enabled?enabled=true' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c17c58c5-b03b-44fc-9ea3-2b3895f19cf9' \
  -H 'cache-control: no-cache' 

-admin gets all users:

curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/users \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 5480e0ca-2483-4afb-a376-9c9a972351bb' \
  -H 'cache-control: no-cache'
  
  -admin gets user by id:
  
  curl -X GET \
  http://localhost:8080/VotingApi/rest/admin/users/100001 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 52771c2c-bfef-410a-8a68-59db257a8bd7' \
  -H 'cache-control: no-cache'
  
  -admin creates user:
  
  curl -X POST \
  http://localhost:8080/VotingApi/rest/admin/users \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c5c63216-2d6c-4fc5-b0c5-c0e789e446fd' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": null,
    "name": "Alex",
    "email": "alex@gmail.com",
    "password": "alex123"
    
}'

-admin updates user:

curl -X PUT \
  http://localhost:8080/VotingApi/rest/admin/users/100023 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 9111bff6-dc3a-4e9d-9aea-5f26df8c7297' \
  -H 'cache-control: no-cache' \
  -d '{
    "id": 100023,
    "name": "Alex125",
    "email": "alex@gmail.com",
    "password": "alex123"
    
}'

-admin deletes user:

curl -X DELETE \
  http://localhost:8080/VotingApi/rest/admin/users/100023 \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: a74dcc0d-ba1f-44ed-bf82-79dc64e9e532' \
  -H 'cache-control: no-cache'
  
  -admin gets user by e-mail:
  
  curl -X GET \
  'http://localhost:8080/VotingApi/rest/admin/users/by?email=admin@gmail.com' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 51a4a051-fa26-4915-b1a4-8ff7c93d6f1a' \
  -H 'cache-control: no-cache'
  
  -admin sets user's enabled parameter:
  
  curl -X PUT \
  'http://localhost:8080/VotingApi/rest/admin/users/100001/enabled?enabled=false' \
  -H 'Authorization: Basic YWRtaW5AZ21haWwuY29tOmFkbWlu' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: fac054a7-ac5e-4655-8bfa-76de6dc2123e' \
  -H 'cache-control: no-cache'
