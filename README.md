Learning Outcomes 
• Design a non-trivial system using object-oriented programming principles. 
• Apply SOLID design principles with concrete evidence. 
• Document software design using UML class and sequence diagrams. 
• Implement an extensible and testable Java system.


Documentation:

- For Characters Package:
MainEntity is the main class from which MainEnemy and MainPlayer will inherit from it. MainEntity remains abstract so as to force these 2 child classes to implement their own methods. From that, MainEnemy extends to EnemyWolf and EnemyGoblin, MainEnemy is abstract as well, this allows the abstract methods inherited from MainEntity to be passed down to EnemyWolf and EnemyGoblin. The same is applied for the player part. After that MainEntity implements an interface named EntityAction and Tickcool down to allow for better scalability for future additions to any enemy/player class.

- For Difficulty Package:
Individually split difficulty into 3 different classes, Difficulty EASY/MEDIUM/HARD. This allows for code to be much easier to implement and read. Each inherits the base properties of difficulty into them and individually implements their own type of enemies and number of enemies.

 - For Item Package:
Item package contains Items class that splits into its own different items type. Here, Potion, PowerStone and SmokeBomb takes in different parameters, therefore, to maintain Polymorphism, applyEffect() that is implemented throughout all 3 subclasses takes in GameSession session as a paramenter. Why this works is that by passing GameSession, we pass an object that contains all the necessary context for the different items. This maintains polymorphism as all the flags such as healing the character or using the powerstone/smokebomb all lives in gamesession, which means by passing it, we allow the individual methods in the item package to use the flags as they see fit. 

- For Game Package:
Game package simply contains the whole game session. In the game session file there is an if else block that differentiates the different difficulty level. Game Session has been split into its individual difficulty level so that it becomes easier to manage and debug the individual parts

Current Entities include: 
Warrior (With Stun ability, preventing the enemies from taking a turn for their next 2 turns)
Wizard (With an AOE blast effect against all enemeis, buffing attack for each enemy killed with the skill)
Wolf (High health enemy that can take a lot of damage but lacks defense and is very fast)
Goblin (Standard mob enemy, standard health, attack, health, defense and speed.)

PowerStone (Allows for one free usage of the skill without incurring skill cooldown)
Health Potion (Heals the player for a fixed set amount)
SmokeBomb (Prevents the enemies from dealing damage for enemy turns)

Each entity has their own fixed action value which ticks down until their action value reaches 0, in which they will proceed to take a turn. After turn is done, AV resets. Each increase in difficulty will incurr more waves of enemies, with Easy difficulty only have 1 wave and hard having 3 waves. The player is only allowed to take 2 items with them and the items can stack. 

Update:
Architectural Refactor — SOLID Principles & Design Patterns: 
Performed a comprehensive refactor of the entire codebase to conform to SOLID design principles, with particular emphasis on the Open-Closed Principle (OCP) and Single Responsibility Principle (SRP). Combat behaviour, status effects, and turn ordering are now expressed as Strategy Pattern abstractions, meaning new behaviours can be introduced by implementing existing interfaces rather than modifying existing classes. This eliminated the shotgun surgery problem where a single change previously required edits across multiple unrelated classes.
Introduced modern Java idioms throughout, including Stream API usage for entity filtering, List.of() for immutable target construction, enhanced switch expressions for player input dispatch, and removeIf() for expired status effect pruning.

Game Mechanics & Combat Refactor: 
Enemy Collection Migration — Replaced raw MainEntity[] arrays in MainGameSession with List<MainEntity>, enabling dynamic enemy management during wave transitions and eliminating manual index tracking. This also allows removeIf() and Stream filtering for living-enemy queries without auxiliary loop logic.

Strategy Integration in GameApp — Modified GameApp to construct GameSession instances via constructor injection, passing concrete ActionStrategy, TurnOrderStrategy, and Skill implementations at the composition root. This ensures GameSession depends solely on abstractions rather than concrete classes, satisfying the Dependency Inversion Principle.

Turn Order Abstraction — Extracted ATB hard coded tick logic from GameSession into a dedicated ATBTurnOrderStrategy implementing the TurnOrderStrategy interface. GameSession now delegates actor selection entirely to the strategy, containing zero AV arithmetic of its own.

Status Effects System: 
Introduced a TickingStatusEffect interface to model effects that persist across turns and expire after a fixed duration. Concrete implementations include:
HealEffect — Applies a flat health restoration per turn for a specified duration, expiring automatically once the tick count is exhausted.
AttackBuffEffect — Temporarily raises the entity's effective attack stat, reverting the bonus on expiry via onTurnEnd() to prevent permanent stat inflation.
DefenseBuffEffect — Mirrors AttackBuffEffect for the defense stat, applied by the defend skill and ticked down by GameSession's tickStatus() each turn.
StunEffect — Sets the entity's stun flag on application and clears it on expiry, preventing the affected combatant from acting for the specified duration.
All effects self-manage their duration internally and register themselves onto the entity's effect list via apply(), keeping call sites in Skill implementations clean and consistent.

Combat Action Strategies: 
Introduced explicit Skill implementations for player combat actions, each encapsulating its own cooldown state and targeting requirement:
SingleTarget — Computes damage against one selected enemy, applies a StunEffect on hit, and resets its cooldown to maxCooldown on use. Returns computed damage after calling takeDamage() on the target directly.
AoeTarget — Iterates the full living-enemy list, applying the damage formula independently to each target. Declared via isAOE() on the Skill interface, allowing selectTargets() in GameSession to automatically pass the full enemy list without caller-side branching.
Both strategies implement cooldown management internally — skillTickDown() decrements the counter each player turn and isUsable() gates activation, preventing GameSession from needing any cooldown awareness.

Item & Inventory Refactor: 
Inventory Class — Enhanced to track item quantities per slot rather than duplicating item instances, improving memory efficiency and simplifying display logic. Inventory UI now renders item name, quantity, and effect description in a single formatted pass.
Item Class — Removed the ItemEffect interface dependency, streamlining item usage into a direct use(MainEntity target) method per item class. This eliminates an unnecessary layer of indirection that previously complicated item effect resolution.
Item Implementations — Potion, PowerStone, and SmokeBomb updated to apply their effects via the new TickingStatusEffect system — Potion applies HealEffect, PowerStone applies AttackBuffEffect for the wizard class, and SmokeBomb applies a timed smoke status — ensuring all item effects participate in the same turn-based expiry lifecycle as combat skills.


