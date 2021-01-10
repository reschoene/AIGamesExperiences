# AI Movement Game
Simple game experience for exploring the use of machine learnig to move a character

## About the game
This game is quite simple, its goal is just to escape the room by moving your character. 

## About the experiment
In this experiment, I explore the use of machine learning for learning the best way to scape the room.

In this game there are 3 possible players:
 * The human player that moves its character using the arrow keys
 * The Trainings AI Players. The amount of AI players in learning process may vary from one to thousands.
 * The Trained Ai Player. Once the training was completed, is possible to use the obtained knowledge. 
 
 # About training process
 Initially the AI players will try to move randomly on the room. When one AI players found the room's exit, its movement sequence is tranfered to the others AI players, and then scene is restarted.
 The AI players starts to move using the previous obtained sequence of movements, using mutations. when calculating the next movement, there is a chance of respecting the next movement from the sequence or try a new one.
 In this way, on each new generation of AI players, there's a chance of appearing a new better sequence of movements (smaller movements sequences are better). If appears a new better sequence, then it is transfered again to the new players and the scene is restarted again.
 When the better obtained sequence is considered the a well trained sequence (I considere this when sequence movement count is smaller than 120 movements), than the training process is completed.
 Once the well trained sequence is obtained, it is stored on a file with name "scoreIA.txt".
 
 # About trained AI player
 This players starts loading the previous stored knowledge from the file named "scoreIA.txt". It contains the best sequence obtained from anothers AI players on its training.
 When the game is started, the trained AI players just move using this sequence of movements
 
 
 
 
