# The Upside Down - Minecraft Mod

A Stranger Things inspired dimension mod for Minecraft 1.21.1 (Fabric).

## Features

### 🌑 The Upside Down Dimension
- **1:1 Mirror of the Overworld** - Same terrain, same structures, same biomes
- **Corrupted Blocks** - Grass becomes blue-purple, stone has red veins, leaves are dead
- **Dark Atmosphere** - Perpetual twilight, no sun or moon
- **Atmospheric Particles** - Floating spores and falling ash

### 🔄 Two Sync Modes

#### Mirror Mode (Default)
- Terrain always mirrors the Overworld
- Player-built structures appear in the Upside Down with decay effects
- Vines and cobwebs cover your creations
- New builds sync every time you enter

#### Snapshot Mode
- Entire Overworld copied once on first entry
- Dimensions become independent after snapshot
- Perfect for "frozen in time" creepy worlds

### ⚙️ Configuration
- Configurable particle density for performance
- Fog density settings
- Sync mode selection

## Installation

1. Install [Fabric Loader](https://fabricmc.net/) for Minecraft 1.21.1
2. Install [Fabric API](https://modrinth.com/mod/fabric-api)
3. Download the mod JAR from releases
4. Place in your `mods` folder

## Building from Source

```bash
# Clone the repository
git clone https://github.com/copyrightedmusicncs-cloud/upsidedown-mod.git
cd upsidedown-mod

# Build the mod
./gradlew build

# Run the client for testing
./gradlew runClient
```

## Project Structure

```
src/
├── main/java/com/upsidedown/
│   ├── UpsideDownMod.java          # Main entry point
│   ├── block/                      # Custom blocks
│   ├── config/                     # Configuration system
│   ├── dimension/                  # Dimension registration
│   ├── item/                       # Items (Portal Catalyst)
│   ├── particle/                   # Custom particles
│   └── sync/                       # Structure sync system
│
├── client/java/com/upsidedown/client/
│   ├── UpsideDownClient.java       # Client entry point
│   ├── UpsideDownSkyRenderer.java  # Custom sky
│   └── UpsideDownParticleSpawner.java
│
└── main/resources/
    ├── data/upsidedown/dimension/  # Dimension JSON
    └── assets/upsidedown/          # Textures, models, lang
```

## Roadmap

### Phase 1 (Current)
- [x] Core dimension system
- [x] Block transformations
- [x] Custom sky renderer
- [x] Particle effects
- [x] Config system
- [x] Mirror/Snapshot sync modes
- [ ] Custom textures (in progress)

### Phase 2 (Future)
- [ ] Red lightning every ~7 seconds
- [ ] Ambient eerie sounds
- [ ] Demogorgon entity
- [ ] Demodogs
- [ ] Mind Flayer presence

### Phase 3 (Future)
- [ ] Portal/rift mechanics
- [ ] Special abilities
- [ ] Multiplayer events

## License

MIT License

## Credits

Inspired by Stranger Things (Netflix)
