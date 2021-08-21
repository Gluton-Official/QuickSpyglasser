# Quick Spyglasser [![CurseForge](http://cf.way2muchnoise.eu/full_494512_downloads.svg)](https://www.curseforge.com/minecraft/mc-mods/quick-spyglasser "Quick Spyglasser on CurseForge")![CurseForge](http://cf.way2muchnoise.eu/versions/494512.svg)

Adds a keybind for using a spyglass anywhere in your inventory. (default key: C)

Can be used client-side only, but server-side presence allows changing the item required to use the keybind.

**This mod is only for _Fabric_, Forge support is planned once it's out of beta!**

Now with Trinkets support! By default, a spyglass can be equipped in your face slot if the 
[Trinkets](https://www.curseforge.com/minecraft/mc-mods/trinkets-fabric) mod is present on both client and server, 
as well as Quick Spyglasser being present on both sides.

### Mod Compatibility
| Mod | Dependency | Side |
| --- | --- | --- |
| [Cloth Config API](https://www.curseforge.com/minecraft/mc-mods/cloth-config) | required | respective |
| [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api) | required | respective |
| [Trinkets*](https://www.curseforge.com/minecraft/mc-mods/trinkets-fabric) | optional | both |
| [Mod Menu](https://www.curseforge.com/minecraft/mc-mods/modmenu) | optional | client |
*Version must be ≥3.0.2

## Config options
#### Client (editable in Mod Menu settings)
- show spyglass overlay
- play spyglass sound
- use cinematic mode when zoomed
- set mouse sensitivity when zoomed
#### Server
- set item required for using the keybind
#### Commands
- `/(quickspyglasser|qs) config quickSpyglassItemId [<item>]`

## TODO
- Spyglass model rendering and animations
- Forge port
