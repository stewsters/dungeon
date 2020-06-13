package game.action

import game.world.Entity
import game.world.World


class Wait : Action {

    override fun onPerform(world: World, entity: Entity): ActionResult {
        // waste a turn
        return Succeeded
    }

}