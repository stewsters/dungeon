package game.world

import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.xy
import game.ai.AI
import math.RangedValue
import math.Vec2
import tileSize

class Entity(
        var pos: Vec2,
        var type: CritterType,
        var ai: AI? = null,
        val sprite: Sprite = Sprite(type.standAnimation).xy(pos.x * tileSize, pos.y * tileSize),
        var life: RangedValue? = if (type != null) RangedValue(type.hp) else null,
        val player: Boolean = false,
        var blocks: Boolean = false,
        val stabber: Boolean = false
) {
    fun isAlive(): Boolean = life?.current ?: 0 > 0

}
