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

