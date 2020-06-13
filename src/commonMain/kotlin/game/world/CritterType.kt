package game.world

import com.soywiz.korge.view.SpriteAnimation

enum class CritterType(val hp: Int) {
    BAT(1),
    GOBLIN(5),
    JELLY(5),
    PLAYER(20);


    lateinit var standAnimation: SpriteAnimation
    lateinit var moveAnimation: SpriteAnimation


}

fun enemies(): List<CritterType> = listOf(CritterType.BAT, CritterType.GOBLIN, CritterType.JELLY)

