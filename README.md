Learning Outcomes 
• Design a non-trivial system using object-oriented programming principles. 
• Apply SOLID design principles with concrete evidence. 
• Document software design using UML class and sequence diagrams. 
• Implement an extensible and testable Java system.



UML Diagram:
// insert image here


Documentation:

- For Characters Package:
MainEntity is the main class from which MainEnemy and MainPlayer will inherit from it. MainEntity remains abstract so as to force these 2 child classes to implement their own methods. From that, MainEnemy extends to EnemyWolf and EnemyGoblin, MainEnemy is abstract as well, this allows the abstract methods inherited from MainEntity to be passed down to EnemyWolf and EnemyGoblin. The same is applied for the player part. After that MainEntity implements an interface named EntityAction and Tickcool down to allow for better scalability for future additions to any enemy/player class.

- For Difficulty Package:
Individually split difficulty into 3 different classes, Difficulty EASY/MEDIUM/HARD. This allows for code to be much easier to implement and read. Each inherits the base properties of difficulty into them and individually implements their own type of enemies and number of enemies.

 - For Item Package:
Item package contains Items class that splits into its own different items type. Here, Potion, PowerStone and SmokeBomb takes in different parameters, therefore, to maintain Polymorphism, applyEffect() that is implemented throughout all 3 subclasses takes in GameSession session as a paramenter. Why this works is that by passing GameSession, we pass an object that contains all the necessary context for the different items. This maintains polymorphism as all the flags such as healing the character or using the powerstone/smokebomb all lives in gamesession, which means by passing it, we allow the individual methods in the item package to use the flags as they see fit. 

- For Game Package:
Game package simply contains the whole game session. In the game session file there is an if else block that differentiates the different difficulty level. Though now thinking about it maybe I could separately declare the files into GameSessionEasy and whatsoever to help reduce the amount of code required in one singular file and also makes it much easier to debug




