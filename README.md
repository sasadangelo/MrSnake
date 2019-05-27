# Mr Snake

Mr. Snake is a clone of the Snake game that in the 90s was released with Nokia phones. The game provides a playing field consists of a grid 10 × 12 in which moves a snake, initially consists of a head, a tail and a body piece. The aim of the game is to acquire the most points by eating apples, cherries and oranges without the snake eat itself. For each fruit eaten the snake gets longer a piece making it harder to not eat himself.

This game has been created only for educational purpose, it has no claim to be a complete game distributable through the Android market. It's my belief that you can get inspiration from this source code to implement your own video games.

# Screenshots

![Main Menu](https://raw.githubusercontent.com/wiki/sasadangelo/MrSnake/img/Screenshot_Mr_Snake_Home.png) ![Game](https://raw.githubusercontent.com/wiki/sasadangelo/MrSnake/img/Screenshot_Mr_Snake.png)

# Video Demo
[![Video Demo](https://raw.githubusercontent.com/wiki/sasadangelo/MrSnake/img/MrSnake_Video.png)](https://www.youtube.com/watch?v=UM2sEq6jmIU "Video Demo")

# Limitations

Currently the game has only one level where the snake has an infinite theoretical life.

# Credits

The author of the framework code, later modified by me, is [Mario Zachner](https://github.com/badlogic) (@badlogic) that released the code with GPL3 license as a resource of the Beginning Android Games book. The framework is a very simplified version of the open source library Libgdx released under GPL3 license. The code of the video game is an evolution of the Mr Nom project released by Mario Zachner with GPL3 license as a resource of the same book.

# License
[GPL3](https://www.gnu.org/licenses/gpl-3.0.en.html)

# Related Projects

[Droids](https://github.com/sasadangelo/Droids), [Alien Invaders](https://github.com/sasadangelo/AlienInvaders)

# Installation & Run

Enable USB Debugging mode that on many devices from 3.2 up to 4.0 (excluded) is in Settings>Applications>Development. On devices from Android 4.0 and later, you’ll find them in Settings>Developer Options. On Android 4.2 and later the option is hidden by default. To make it reappear, go to Setting->Info on the device and tap Version Build 7 times, the option will become visible.

Enable installation from Unknown Sources clicking on Settings->Security.

Download the application [clicking here](https://github.com/sasadangelo/MrSnake/releases/download/0.0.5/mrsnake.apk) and install it.

# Installation & Run from source code

[Download and install Android Studio](http://code4projects.altervista.org/how-to-install-android-studio/). To do that accept all the default settings the installation procedure show you. If you already have Android Studio installed, make sure it is at the latest level. Once Android Studio is up and running make sure all projects are closed (if a project is open do File->Close Project), the "Welcome to Android Studio" Panel appears. Select the option "Check out project from version control" and then GitHub. 

Fill the following fields:

    Git Repository URL: https://github.com/sasadangelo/MrSnake.git
    Parent Directory: <an empty directory previously created>
    Directory Name: MrSnake

The source code will be downloaded and the MrSnake project will be created. Now you can run the code doing Run->Run. You can execute the code on Physical or Virtual device. For more details you can read the last section of the following [article](http://code4projects.altervista.org/how-to-create-an-android-application/). 

# Troubleshooting

Sometime could happen that there is incompatibility between the level of gradle declared in the source code with the one installed in the development environment. When this occurs Android Studio will show also a link to fix it. Click the link to solve the issue.
