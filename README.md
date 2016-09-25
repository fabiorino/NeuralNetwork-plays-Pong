# Neural Network plays Pong
This is my first approach to Neural Networks (and AI more generally). I have created a clone of Atari Pong and a simple **C**onvolutional **N**eural **N**etwork that learns how to play it.

![ScreenShot](http://i.imgur.com/e8b6W65.png)

## Resources
The most useful resources I have used to learn the basics of Neural Networks are:
* A **[Steven Miller](http://stevenmiller888.github.io/)**'s [article](http://stevenmiller888.github.io/mind-how-to-build-a-neural-network/). He explains really clearly and simply what is a Neural Network and how it works.
* An **[Ivan Seidel](https://github.com/ivanseidel)**'s [video](https://www.youtube.com/watch?v=P7XHzqZjXQs). He explains what genetic algorithms are and how he has created a Neural Network that learns how to play the Google Chrome's dinosaur Easter egg game.

If you have never worked with Neural Networks before, I would really suggest watching the video and reading the article before going any further.

## Play the game
### Overview
The neural network controls the left paddle. You can control the right paddle or let the computer play for you (it will never fail; this is useful to train the Neural Network while you do something else).

### Controls
**Spacebar**: enables/disables auto play for the right paddle.
<br />
**Up/Down arrows**: If auto play is disabled, use these arrows to control the right paddle.

### synapses.txt
`synapses.txt` is a file that the neural network automatically generates. Each line contains the values of the synapses of the current generation.
* If the file exists, when you run the game, the last generation contained in this file will be automatically loaded.
* If the file does not exists, it will be created. For each new generation, a new line will be created at the end of the file.

## Source Code
### Compile the source code
I have used Netbeans to compile the whole project. [Download Netbeans](https://netbeans.org/downloads/), download `NetbeansProject.zip` and open it with Netbeans.

### Packages and files
**Pong**
* `Pong.java`: contains the main function. Here is where both the Neural Network and the game are initialized. Also, here is where the outputs of the Neural Network are computed and "converted" into an action.
* `Panel.java`: all the graphics of the game are rendered here. Also, it contains the keyboard listeners.
* `Paddle.java`: the source code to move the left and the right paddles.
* `Ball.java`: the source code to move the ball and repaint the panel. Also, here is where the collisions between the ball and the borders/paddles are handled and the scores are updated.

**NeuralNetwork**
* `NeuralNetwork.java`: the heart of the whole project. I will explain how the Neural Network works later in this documentation.
* `SaveLoad.java`: it saves/loads the file `synapses.txt`.
* `LiveView.java`: it renders the neural network live view. It generates the JFrame and all the components dynamically.

## How the Neural Network works
### Genetic algorithm
The Neural Network will start with completely random synapses. This will inevitably lead to not score any points. When a game over happens, a new genome will be tested. When all the genomes of the current generation are tested, a new generation will be created:

**If none of the genomes scored a point**
<br />
A new generation will be created completely randomly.

**If one genome scored a point**
<br />
A new generation will be created by mixing random synapses values with the values of the genome that scored a point.

**If more than a genome scored a point**
<br />
 A new generation will be created by mixing the best genome with the others. Nevertheless, random synapses values are still generated.

### Inputs and Outputs
There are **two inputs**:
<br />
1. The y coordinate of the left paddle.
2. The y coordinate of the ball.
3. Technically, there is a third input: the bias. It is always equal to one.

There is only **one output**. The output value is a number between zero and one.
<br />
* If output <= 0.5, the paddle will go down.
* If output > 0.5, the paddle will go up.