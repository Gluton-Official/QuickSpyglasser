### v0.3.0
- Added Trinkets support
- Implemented server-side config
- Added command to change server config
- Invalid itemId in config resets to previous instead of default

### v0.2.2
- Cloth Config is now a non-embedded required dependency to reduce jar size [#4](https://github.com/Gluton-Official/QuickSpyglasser/issues/4)
- Fixed crashing with Origins [#3](https://github.com/Gluton-Official/QuickSpyglasser/issues/3)

### v0.2.1
- Fixed keybind not requiring configurated item without saving the config first
- Removed keybind item required from in game config (still configurable in config file)
- Added zoomed sensitivity config option

### v0.2.0
- Added config options when using the spyglass with keybind (does not apply to right-click spyglass usages)
  - enable/disable spyglass overlay
  - enable/disable spyglass sound
  - enable/disable cinematic mode
  - set item required for using the keybind
- Added Cloth Config integration (included library)
- Added ModMenu optional dependency
- Fixed some stuff in fabric.mod.json 

### v0.1.0
- Initial release
- Adds a keybind for using a spyglass in your inventory