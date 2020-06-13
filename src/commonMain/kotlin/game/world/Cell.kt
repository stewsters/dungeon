package game.world

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.xy
import tileSize

class Cell(
        x: Int,
        y: Int,
        var tileType: TileType,
        val sprite: Sprite = Sprite(tileType.animation).xy(x * tileSize, y * tileSize)
) {
    fun rebuild() {
        sprite.playAnimation(tileType.animation)
    }
}