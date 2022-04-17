=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: vzammit
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. LinkedLists - I implemented LinkedLists for my Snake object. This is because LinkedList
  has insertion which was very helpful when I needed to increase the size of the Snake each
  time it successfully eats an apple or candy. LinkedLists was used to add SnakeParts to the tail of the list when
  a Snake eats an apple or candy, deleting SnakeParts from the tail when it ate poison,
  and retrieving the position of the head of the Snake. LinkedLists was very helpful when I needed to
  update the movement of the snake and increment the position and the direction of movements,
  as well as checking if the snake crashed with the walls or itself. Because my Snake can grow and shrink,
  using LinkedLists was very appropriate for my feature.

  2. File I/O - I implemented File I/O to enable persistent state between runs of my game.
  I am able to store the entire game board and the history of all moves made and the current state of the Snake
  and all of the other foods. I also used File I/O to record two states of the game, the score and the
  total time played for a game. I then keep track of the history of the scores and times and present the user
  with their top 3 scores. The user can also see their current score and time.

  3. Inheritance - I implemented inheritance with my GameObject class as the super class, and all of the different
  foods and SnakeParts as the subclasses. Having GameObject as the super class was very helpful because all of the
  foods and snake parts have positions, can update scores, can intersect with the snake, etc. so having one parent
  class to store all of those fields and accessor and mutator methods was very appropriate to make my code more
  efficient. Because I had 3 different types of foods, I used inheritance when creating a Food super class for the
  3 different foods as subclasses. This is because each food has their positions on the court but they also
  can affect the score, the length of the snake, and the color of the snake as well. So I created abstract methods
  in the Food class that each individual foods could override based on their specific features.

  4. 2D Array - I used 2D arrays to keep track of the progress of the player. Because the game is keeping track of
  the score and the time played, it was very convenient to store these values in a 2D array. The 2D arrays worked
  hand in hand with my File I/O because as it read through the scores and times, having the values stored in an int
  2D array was efficient and understandable to implement.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

GameCourt - This class contains all of the main logic for the game and how the different
objects work together. It instantiates all of the various GameObjects and the snake
and controls how they interact with each other by invoking certain methods from each class.
It also implements the keyboard elements and handles when the user presses different keys
to make the game going. GameCourt also can reset the game after it finishes and implements a
timer that is used to update the game after every move.

GameObject - This class is the superclass for all of the different game objects, such
as the various foods and Snake parts. This class creates instance fields for the coordinate
locations of the objects and their size. It also has accessor and mutator methods that are helpful
to call in the child classes and GameCourt. It also creates an abstract draw method that the
child classes override.

SnakePart - This class is a subclass of GameObject and represents a body part of the snake.
This class checks if a body part hits any of the walls or intersects with a food object.

Snake - This class creates a LinkedList of SnakeParts to create an overall snake. It has methods
that keep track of how the snake can grow or shrink depending on what it eats. It also controls the
overall movement of the snake based on the keyboard presses from the user and how it can change directions
based on what keys are pressed. The Snake class also keeps track of if the head of the snake collides
with any part oof the snake's body, as well as if the snake hits the walls.

Food - This class is a subclass of GameObject. This class has methods on how the different foods can
update the score or change the snake's color and size. These are abstract methods for the child classes
to override based on each individual's characteristics. Another method it creates is how the food object
is put back onto the board after it was eaten.

Apple - This class is a subclass of Food. It keeps track of how the snake is affected if it is eaten.
It overrides methods from the Food class on how it updates the score, change the snake's color back
to green, how it changes the snake's length, and how it respawns on the court.

Candy - This class is a subclass of Food. It keeps track of how the snake is affected if it is eaten.
It overrides methods from the Food class on how it updates the score, change the snake's color to
any random color, how it changes the snake's length, and how it respawns on the court.

Poison - This class is a subclass of Food. It keeps track of how the snake is affected if it is eaten.
It overrides methods from the Food class on how it negatively affects the score, change the snake's color back
to grey, how it causes the snake's length to shrink, and how it respawns on the court.

RunSnake - This class contains the GUI elements of the game and the File
I/O to read through scores. It also sets up the game display using frame
and panel.

Game - Runs the game using RunSnake in the main method

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

Yes there were many stumbling blocks while I was implementing my game. I initially had trouble with moving my snake.
The switch in directions when the keys were pressed was not working. This is because at first, I gave each SnakePart
it's own direction and tried to move them all when the snake moved. This was hard though because I had to
iterate through the list and keep updating each part which was not efficient. Additionally, in the GameCourt method
when I was changing the direction based on key presses, it was hard to keep track of which direction on the heap that
was being affected. Because of this confusion and how there were too many things all over the place, I switched my
tactics and changed the way my snake moves. I instead moved the snake by adding and removing heads and the last body
part from the tail. I also struggled with the grids on the board and how to make each food object fit perfectly. I
solved this issue by simply hardcoding the proper dimensions though, which was much easier than creating methods.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

Overall, I think my design was pretty good. Each class definitely has a separate functionality
and the only similarities are between the GameObject and the Food class. And even so, the separation of those
two classes were necessary because a food object and a snake part have very different functionalities.
I tried to stay on top of encapsulation, such as returning the body of the snake or the colors to make sure private
state encapsulation is maintained. If I had more time, I would try to make my respawn method more seamless and maybe
update the File I/O.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

Images for Apple, Poison, and Candy
Used some example code from mushroom of doom game