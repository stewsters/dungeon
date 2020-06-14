package game.world

import com.soywiz.korge.view.SpriteAnimation


enum class TileType(val blocks: Boolean, val tunnelCost: Double) {
    WALL(true, 4.0),
    DIRT(true, 2.0),
    FLOOR(false, 1.0),
    DOOR_CLOSED(true, 1.0),
    DOOR_OPEN(false, 1.0),
    WATER_SHALLOW(false, 2.0),
    WATER_DEEP(true, 4.0); // TODO: bats can fly over it?


    lateinit var animation: SpriteAnimation

}