# Jarkanoid
A clone of the original Arkanoid game made by Taito. The naming scheme is inspired by the Java programming language and is not related to JARKANOÏD made by Jérôme BARJOT.

# Introduction
Jarkanoid is a java clone of the original Arkanoid game where the objective of the game is to successfully remove the blocks on the screen without losing all of your lives. Due to the age of this game, for anyone use to modern games this might be quite slow; the original game Arkanoid is also rarely available in very retro arcades. The design patterns here also don't reflect any modern techniques such as uncoupling drawing and logic calls.

# Setup
The following are required to use this plugin.
- Java (**7+**)
Optional
- Eclipse# (**3.0+**)

To run this simply import the project into eclipse and run the [ControlPanel.Java](https://github.com/JohnSongNow/jarkanoid/blob/master/src/ControlPanel.java) file inside the src package.

# Rules

The objective of this game is to destroy all the blocks in a given level. You are given a platform to control that is used to redirect and launch balls. Each time the ball makes contact with a block it will deal a certain damage to that block. The ball will bounce off of the platform that you control and you will lose 1 life per ball that is not caught. Powerups will ocassically spawn with each destroyed block. 

# Controls
|Action|Key|
|---|---|
|Movement|Right/Left Mouse or Arrow Keys|
|Pause|P|
|Fire Laser|LMB (**Left-Mouse-Button**)|
|Fire Caught Ball|LMB (**Left-Mouse-Button**)|
|Launch WMD (**Omar's Ball**)|O + Q (**In succession**)|

# Powerups
|Name|Description|Exclusion|
|---|---|---|
|Catch|Allows balls to be caught on the rest|Laser|
|Speedup|Increases the ballspeed||
|Enlarge|Enlarges the platform width|Laser|
|Multiball|Spawns 2 adjacent balls with different trajectory where the ball currently is||
|Laser|Transforms the platform into a gunner with two side lasers|Catch, Enlarge|

# Example
![alt text](https://github.com/JohnSongNow/jarkanoid/blob/master/Readme/ss3.png "Laser Examplle")


# Future Works
I am unlikely to ever continue working on this due to how old this project was, however if you're interested in forking and working on this project here are some of the things that can be very helpful to implmenent!
- Persistant High Scores
- Custom Levels
- Settings Options
- Intro/Losing Screen
