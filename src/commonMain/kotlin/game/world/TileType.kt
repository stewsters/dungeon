package game.world

import com.soywiz.korge.view.SpriteAnimation


enum class TileType(val blocks: Boolean) {
    WALL(true),
    DIRT(true),
    FLOOR(false),
    DOOR_CLOSED(true),
    DOOR_OPEN(false),
    WATER_SHALLOW(false),
    WATER_DEEP(true); // TODO: bats can fly over it?

    lateinit var animation: SpriteAnimation

}