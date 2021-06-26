# 8Core (Work-In-Progress)

## Introduction
8Core is a completely free to use and open-source spigot server codebase with core features:

- Ranks and Permissions
- Punishment System
- Friends System
- Preferences System
- Command Manager
- Plugin Messaging (Staff and Direct Messages, Party Chat, Friend Requests)
- Report System
- Module System:
   - Hub Module:
      - CosmeticsAPI
      - Join Items
   - Game Module (TODO):
      - Minigame API
      - Map System

8Core is easy for developers to build on, as all classes are well documented and easy to use.
Please note that this repository is intended for developers and the customization by file is limited.
What can be customized by file?
- Server Info (Name, Domains)
- Primary Colors
- Tablist in Hub
- Scoreboard in Hub

## Usage
8Core is currently in development stages and is not ready for public use. 

Contributions are appreciated and will be credited.

## Build
To build this project, clone this repository.

**Core:**
Navigate to the core root and execute
   
     gradlew shadowJar

**Bungee:** *(Needed for staff chat and friend chat to be global)*
Navigate to the bungee root and execute

    gradlew shadowJar

*Prebuilt jars will not be available until development is completed*
