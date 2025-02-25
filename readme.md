# SetNameColor Plugin

## Overview

SetNameColor is a simple Minecraft plugin that allows players to customize their name color in chat. It supports both named colors and hex color codes.

## Features

- Players can set their chat name color using a command.
- Supports both predefined named colors and custom hex color codes.
- Colors persist between server restarts.
- Simple permission system to control who can change their name color.

## Installation

1. Download the latest release of `SetNameColor.jar`.
2. Place the `/build/libs/SetNameColor-1.0-SNAPSHOT.jar` file into the `plugins` folder of your Minecraft server.
3. Restart the server to enable the plugin.

## Commands

| Command         | Usage               | Description                                                             |
|-----------------|---------------------|-------------------------------------------------------------------------|
| `/setnamecolor` | `<color / #rrggbb>` | Sets the player's name color in chat. Accepts named colors or hex codes |

### Example Usage

- `/setnamecolor red` → Sets the player's name color to red.
- `/setnamecolor #ff5733` → Sets the player's name color to a custom hex color.

## Permissions

| Permission         | Description                                | Default |
|--------------------|--------------------------------------------|---------|
| `setnamecolor.set` | Allows the player to set their name color. | `true`  |

## Configuration

The plugin saves player colors in `playerColors.yml` located in the plugin's data folder. This file is automatically managed by the plugin.

## Dependencies

- Requires Minecraft `1.21+` (API version `1.21`).
- Uses [Adventure API](https://docs.adventure.kyori.net) for text formatting.

## Development

### Main Classes

- `SetNameColor.java` - Manages the plugin lifecycle and stores player colors.
- `SetNameColorCommand.java` - Handles the `/setnamecolor` command.
- `PlayerChatListener.java` - Updates chat messages to display player colors.

### Building from Source

1. Clone the repository.
2. Build using `./gradlew build`
3. Place the compiled `.jar` into your server's `plugins` folder.

## License

This plugin is provided as open-source. Feel free to modify and use it according to your needs.

---

Enjoy customizing your chat name colors! 🎨
