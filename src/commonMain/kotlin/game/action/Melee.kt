package game.action

import GameState
import game.world.Entity
import game.world.World
import gameState
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

        victim.life?.damage(1) // TODO: this need variation
        swing.play()

        if (victim.life!!.current <= 0) {
            victim.life = null
            victim.ai = null

            victim.blocks = false
            victim.sprite.removeFromParent()
//            victim.sprite.rotation = 90.degrees
            if (victim.player) {
                gameState = GameState.LOST
            }

        }

        return Succeeded

    }

}