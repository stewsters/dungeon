package game.action

import GameState
import door
import game.world.Decor
import game.world.Entity
import game.world.TileType
import game.world.World
import gameState
import math.Vec2


class Walk(val dir: Vec2) : Action {

    override fun onPerform(world: World, entity: Entity): ActionResult {

        val nextPos = entity.pos + dir

        if (world.tiles.outside(nextPos)) {
            return Failed
        }

        if (entity.player && world.tiles[nextPos].decor == Decor.CHEST) {
            gameState = GameState.WON
            world.tiles[nextPos].decor = Decor.CHEST_OPEN
            return Succeeded
        }

        val entitiesOnNextSpace = world.entities.filter { it.pos == nextPos }

        val meleeTargets = entitiesOnNextSpace.filter {
            it.isAlive() && it.player != entity.player
        }
        if (meleeTargets.isNotEmpty()) {
            return Melee(meleeTargets.first()).onPerform(world, entity)
        }

        if (entity.player && world.tiles[nextPos].tileType == TileType.DOOR_CLOSED) {
            world.tiles[nextPos].tileType = TileType.DOOR_OPEN
            world.tiles[nextPos].rebuild()
            door.play()
            return Succeeded
        }

        //        // get next step, go there
//        if (entitiesOnNextTile.any { !it.isAlive() }) {
//            // Free loot pickup
//            return Loot().onPerform(world, entity)
//            //return Alternative(Loot())
//        }


        if (world.tiles[nextPos].isBlocked()) {
            //blocked
            return Failed
        }

        entity.pos = nextPos
        if (entity.stabber) {
            val nextPos = entity.pos + dir
            val entitiesOnNextSpace = world.entities.filter { it.pos == nextPos }
            val meleeTargets = entitiesOnNextSpace.filter {
                it.isAlive() && it.player != entity.player
            }
            if (meleeTargets.isNotEmpty()) {
                return Melee(meleeTargets.first()).onPerform(world, entity)
            }
        }

        return Succeeded
    }
}