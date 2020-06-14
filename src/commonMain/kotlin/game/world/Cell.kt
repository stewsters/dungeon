package game.world

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.xy
import tileSize

class Cell(
        x: Int,
        y: Int,
        var tileType: TileType,
        val decor: Decor? = null,
        val sprite: Sprite = Sprite(tileType.animation).xy(x * tileSize, y * tileSize),
        val decorSprite: Sprite? = if (decor != null) Sprite(decor.animation).xy(x * tileSize, y * tileSize) else null
) {
    fun rebuild() {
        sprite.playAnimation(tileType.animation)
        decorSprite?.playAnimation(decor?.animation)
    }

    fun isBlocked(): Boolean = tileType.blocks || decor?.blocks ?: false
}