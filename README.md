# WalletShare
## Android project by [Sachin](https://github.com/MrSachin7) and [Himal](https://github.com/himal28924)

---
The Android software WalletShare allows for excellent money management. The application keeps track of your spending throughout the month and provides you with a thorough breakdown of how you spend your hard-earned cash. Another important aspect of the app is the option to divide the cost with your friends or roommates without worrying about the trouble.

We chose to create this app because of our extremely personal experiences, . Being students, we must be frugal with our expenses. Sometimes we spend a lot of money that is not essential on various things. We think that a detailed breakdown of our spending will help us manage it more effectively.

We frequently go out to dine, and some people pay the bill that must be split between everyone. As the population grows, complexity also grows exponentially. As a result, the app will have a function that enables users to simply share expenses and deftly determine how much money needs to be divided among the group.

---

### Following are the requirements of the project :

 As a user , I want to be able to :-
- [x] Create an account with username, password and email address, so that i can use it and all my information are stored.
- [x] Add and edit balance on my account, so that i can update and use my balance.
- [x] Add an expenditure with appropriate category, date, amount, and a small note so that I can track my expenditures.
- [x] Add new expenditure of my own liking so that i can properly categorize my expenditures.
- [x] Filter my expenditures by category and date.
- [ ] Visualize by expenditures in different graph formats of different categories at different time.
- [ ] Send friend request to other users, so that I can add them as a friend.
- [ ] Accept or reject a friend request, so I can decide who to as a friend. 
- [ ] Create a group, so that I can add friends to share expenditures.
- [ ] Add friends by their username to the group, so I can share expenditures.
- [ ] Add expenditures to the group so that I can share expenditures.
- [ ] Join a group using the group code so i can join a group.
- [ ] Generate a QR code for the group.
- [ ] Scan a QR Code to join the group so its easier to join a group.
 

```java
// Deadline --> December 4, 2022 :: 23:59.
LocalDate deadLineDay = LocalDate.of(2022, 12, 04);
LocalTime deadLineTime = LocalTime.of(23, 59);
LocalDateTime deadLineDateTime = LocalDateTime.of(deadLineDay, deadLineTime);

boolean isDeadline = deadLineDateTime.isBefore(LocalDateTime.now());
        
while(!isDeadline){
    wakeUp();
    breakFast();
    code();
    lunch();
    code();
    dinner();
    code();
    sleep();
    Thread.sleep(8*60*60*1000); //Sleeping for 8h is important 
}
