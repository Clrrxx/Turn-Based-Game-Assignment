Learning Outcomes 
• Design a non-trivial system using object-oriented programming principles. 
• Apply SOLID design principles with concrete evidence. 
• Document software design using UML class and sequence diagrams. 
• Implement an extensible and testable Java system.



UML Diagram:
// insert image here


Documentation:

- For Characters Class:
MainEntity is the main class from which MainEnemy and MainPlayer will inherit from it. MainEntity remains abstract so as to force these 2 child classes to implement their own methods. From that, MainEnemy extends to EnemyWolf and EnemyGoblin, MainEnemy is abstract as well, this allows the abstract methods inherited from MainEntity to be passed down to EnemyWolf and EnemyGoblin. The same is applied for the player part. After




