package game.action

import game.world.Entity
import game.world.World


/**
 * Used to pick up loot
 */
class Loot : Action {

    override fun onPerform(world: World, entity: Entity): ActionResult {

        //TODO: need to figure out what we are doing for loot/gear
        return Succeeded
    }

}