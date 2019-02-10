package ru.lukashev.vote;

import org.slf4j.LoggerFactory;
import ru.lukashev.vote.model.Restaurant;
import org.slf4j.Logger;

public class Main {

    private static final Logger LOG  = LoggerFactory.getLogger(Main.class);


    public static void main(String[] args) {

        Restaurant restaurant1 = new Restaurant(1, "Metropol");
        Restaurant restaurant2 = new Restaurant(2, "Ivanyich");
        Restaurant restaurant3 = new Restaurant(3, "U berez");
        Restaurant restaurant4 = new Restaurant(4, "Koryushka");
        Restaurant restaurant5 = new Restaurant(5, "MyLove");

       /* User user1 = new User(1, "Kolya", restaurant5);
        User user2 = new User(2, "Max", restaurant1);
        User user3 = new User(3, "Ilya", restaurant1);
        User user4 = new User(4, "Petya", restaurant3);
        User user5 = new User(5, "Vika", restaurant3);
        User user6 = new User(6, "Natasha", restaurant2);
        User user7 = new User(7, "Egor", restaurant4);
        User user8 = new User(8, "Sergey", restaurant1);
        User user9 = new User(9, "Alex", restaurant3);
        User user10 = new User(10, "Alexey", restaurant2);
        User user11 = new User(11, "Katya", restaurant5);
*/
     //   List<User> users = List.of(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10, user11);

      /*  System.out.println(VoteUtil.getRatings(users));
        System.out.println("///////////////");
        LOG.debug("getting winner");
        System.out.println(VoteUtil.getWinner(users));
        System.out.println("///////////////");
        System.out.println(VoteUtil.getRatingsInPercents(users));*/

    }
}
