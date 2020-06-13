package game.action

import game.world.Entity
import game.world.World


interface Action {
    fun onPerform(world: World, entity: Entity): ActionResult
}