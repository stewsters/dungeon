package game.world

import com.soywiz.korge.view.SpriteAnimation

enum class CritterType(
        val hp: Int,
        val movement: Int
        // damage
        // to hit - exclude to avoid missing.  Do need it if we want to lower chance with hits
        // dodge/ac/dr?
        // movement
//        val lowDamage: Int = 0
//        val highDamage: Int = 0
//
//        val moveCost: Int = 0
//        val attackCost: Int = 0
//        val doorOpener: Boolean = false
//        val chaser: Boolean = false
) {

    BAT(1, 5),
    GOBLIN(1, 4),
    JELLY(2, 2),

    KNIGHT(4, 4),
    THIEF(15, 5),
    MAGE(10, 3);


    lateinit var standAnimation: SpriteAnimation
    lateinit var moveAnimation: SpriteAnimation


}

fun enemies(): List<CritterType> = listOf(CritterType.BAT, CritterType.GOBLIN, CritterType.JELLY)


//enum class MovementType {
//    STATIONARY, ADVANCE,  // gets into melee.
//    RETREAT,  // gets away from melee. Archers
//    HUNTER,  // paths to you
//    RANDOM,  //Moves randomly
//    STRAIGHT //Moves in straight lines, bounces off of walls
//}

//enum class LocomotionType {
//    GROUNDED,  //collides with grounded obstacles
//    JUMPING,  //can jump over grounded obstacles to a freespace
//    AIRBORNE // unaffected by grounded obstacles
//}