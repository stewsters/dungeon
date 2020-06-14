package game.world

import com.soywiz.korge.view.SpriteAnimation

enum class TileType(val blocks: Boolean, val tunnelCost: Double) {
    WALL(true, 8.0),
    DIRT(true, 2.0),
    FLOOR(false, 1.0),
    DOOR_CLOSED(true, 1.0),
    DOOR_OPEN(false, 1.0),
    DOWN_STAIR(false, 100.0),
    BOOKSHELF(true, 6.0),
    WATER_SHALLOW(false, 2.0),
    WATER_DEEP(true, 4.0); // TODO: bats can fly over it?

    lateinit var animation: SpriteAnimation
}

// decor goes over tiles, and modifies it?
enum class Decor(val blocks: Boolean) {
    TABLE(true),
    WALL_SKELETON(true),
    GREEN_BANNER(true),
    RED_BANNER(true),
    BARREL(true),
    GOLD(true),
    TORCH(true),
    CHEST(true),
    CHEST_OPEN(true);

    lateinit var animation: SpriteAnimation
}