# TriHunt — Agent Instructions

TriHunt is a Manhunt plugin for Paper servers (Kotlin 2.3.10, Minecraft 26.2, Java 25): the Speedrunners race to beat
the chosen boss while the Hunters try to stop them. [README.md](README.md) has the player-facing overview.

## After every change: keep the changelog and docs in sync

Before finishing any task that changes the plugin, do all of the following:

1. **Update the changelog** — add an entry for the change under `## Unreleased` in [CHANGELOG.md](CHANGELOG.md), in the
   same commit as the change. Every new feature gets an entry, and so does every improvement and fix.
    - Follow the SkyHanni-style format documented in [docs/RELEASING.md](docs/RELEASING.md): category (`### New
      Features` / `### Improvements` / `### Fixes` / `### Technical Details` / `### Removed Features`), then a
      `#### Feature Area` heading, then `+` bullets.
    - Reuse the category and feature-area headings already under `## Unreleased` instead of repeating them.
    - Write player- and server-owner-facing entries for gameplay changes; put refactors, build, and tooling changes
      under `### Technical Details`.
    - Never edit the section of a version that has already been released.
    - Skip changelog entries only for changes with no effect on the shipped plugin or its workflow (e.g. fixing a typo
      in a doc).

2. **Update the developer docs** — if the change adds or alters a base class, registrar, utility, or data API, update
   [docs/DEVELOPER_GUIDE.md](docs/DEVELOPER_GUIDE.md) or [docs/UTILITY_GUIDE.md](docs/UTILITY_GUIDE.md) in the same
   task. A change to a documented workflow (e.g. the release process in [docs/RELEASING.md](docs/RELEASING.md)) updates
   that doc too.

3. **Check the README** — if the change affects anything [README.md](README.md) mentions (game modes, features, planned
   features, getting started), update it. When a planned feature ships, move it out of **Planned Features**.

## Build & run

```powershell
./gradlew build        # Builds build/libs/TriHunt-<version>.jar
./gradlew copyPlugin   # Copies the jar into run/plugins/
./gradlew startServer  # Runs copyPlugin, then launches the paper-*.jar in run/
```

## Project conventions

- Plugin code is Kotlin under `src/main/kotlin/net/trilleo/mc/plugins/trihunt/`; `Main.kt` is the entry point.
- **Auto-registration** — `PackageScanner` discovers concrete classes at startup, so there is no manual wiring and no
  `plugin.yml` edit. Extend the right base class and place the file in the right package:

  | Component   | Base Class      | Package                 |
  |:------------|:----------------|:------------------------|
  | Command     | `PluginCommand` | `commands` (any depth)  |
  | Listener    | `Listener`      | `listeners` (any depth) |
  | GUI         | `PluginGUI`     | `guis` (any depth)      |
  | Task        | `PluginTask`    | `tasks` (any depth)     |
  | Item        | `PluginItem`    | `items` (any depth)     |
  | Recipe      | `PluginRecipe`  | `recipes` (any depth)   |
  | Config      | `PluginConfig`  | `config` (any depth)    |
  | Player data | `PlayerData`    | `data`                  |
  | Server data | `ServerData`    | `data`                  |

  Permissions are derived from commands automatically. Every command, listener, GUI, and task needs either a no-arg
  constructor or one accepting a `JavaPlugin`. See [docs/DEVELOPER_GUIDE.md](docs/DEVELOPER_GUIDE.md).
- `plugin_version` in [gradle.properties](gradle.properties) is the single source of truth for the plugin version. It
  flows into the jar filename and, through `processResources`, into `plugin.yml` (`version: ${projectVersion}`) — never
  hardcode a version in `plugin.yml` or `build.gradle.kts`.
- [.github/workflows/build.yml](.github/workflows/build.yml) builds every push and pull request. Releases are made by
  tagging `vX.Y.Z` — see [docs/RELEASING.md](docs/RELEASING.md). **Never tag or push tags unless explicitly asked** —
  pushing a tag publishes a release.
- Commit messages follow the `<tag>: <message>` convention (`Feature:`, `Improvement:`, `Fix:`, `Internal:`,
  `Backend:`, `Update:`) with one granular commit per logical change — see
  [docs/COMMIT_STRUCTURE.md](docs/COMMIT_STRUCTURE.md). A `Feature:`, `Improvement:`, or `Fix:` commit carries its own
  changelog entry.
