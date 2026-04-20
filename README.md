<h1 align="center">
  TriHunt: Modern Minecraft Manhunt Plugin
</h1>

A Minecraft Manhunt plugin built with Kotlin for Paper. It comes with an auto-registration system for commands,
listeners, permissions, and GUIs — just extend a base class, drop it in the right package, and the plugin handles the
rest.

## Project Structure

```
src/main/kotlin/net/trilleo/mc/plugins/trihunt/
├── Main.kt                  # Plugin entry point
├── commands/                # Auto-registered commands (extend PluginCommand)
├── config/                  # Typed configuration wrapper (PluginConfig)
├── guis/                    # Auto-registered GUIs (extend PluginGUI)
├── listeners/               # Auto-registered listeners (implement Listener)
├── registration/            # Auto-registration framework
└── utils/                   # Utility helpers (e.g. ItemStack DSL)
```

See [`docs/DEVELOPER_GUIDE.md`](docs/DEVELOPER_GUIDE.md) for detailed instructions on creating commands, listeners,
GUIs, and working with the configuration system.
