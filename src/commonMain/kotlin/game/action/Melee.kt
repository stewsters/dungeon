package game.action

import com.soywiz.korma.geom.degrees
import game.world.Entity
import game.world.World
import math.getChebyshevDistance
import swing


class Melee(
        val victim: Entity
) : Action {

    override fun onPerform(world: World, entity: Entity): ActionResult {

        if (victim.player == entity.player)
            return Failed

        if (getChebyshevDistance(victim.pos, entity.pos) > 1) {
            return Failed
        }

        victim.life?.damage(5) // TODO: this probably needs to be
        swing.play()

        if (victim.life!!.current <= 0) {
            victim.life = null
            victim.ai = null

            victim.sprite.removeFromParent()
//            victim.sprite.rotation = 90.degrees

        }

        return Succeeded

    }

}