# UozuBot
My Starcraft 1 bot. Currently mines minerals until the
enemy comes and kills it.

# Dependencies
- [BWAPI](http://bwapi.github.io/) (tested with 4.1.0-Beta)
- 32 bit JRE (tested with Oracle JDK 1.7.0_79 & 1.8.0_65)

# Running the bot
- NOTE: the game will appear to freeze at the start of a game
  on a new map. This is due to the map analyser running.

- Download dependencies
- Option 1: Gradle
  - run `gradlew run` at the root directory of this project
  - Start Starcraft using Chaoslauncher, start a game
- Option 2: IntelliJ IDEA
  - Open project in Intellij IDEA
  - Run UozuBotManager
  - Start Starcraft using Chaoslauncher, start a game