package game.action

import game.world.Entity
import game.world.World
//import kaiju.math.getEuclideanDistance
//import kaiju.math.manhattanDistance
//import kaiju.pathfinder.findPath2d
import math.Vec2
import math.findPath2d
import math.getEuclideanDistance
import math.getManhattanDistance

class WalkToPoint(
        val destination: Vec2
) : Action {

    override fun onPerform(world: World, entity: Entity): ActionResult {

        if (entity.pos == destination)
            return Succeeded

        if (getManhattanDistance(destination, entity.pos) <= 1) {
            val meleeTargets = world.entities.filter {
                it.pos == destination && it.isAlive() && it.player != entity.player
            }
            if (meleeTargets.isNotEmpty()) {
                return Melee(meleeTargets.first()).onPerform(world, entity)
            }
        }

//        val start = System.currentTimeMillis()
        // path to point
        val path = findPath2d(
                size = world.tiles.getSize(),
                cost = { 1.0 },
                heuristic = { one, two -> getEuclideanDistance(one, two) },
                neighbors = { our ->
                    our.vonNeumanNeighborhood().filter { vec ->
                        world.tiles.contains(vec) &&
                                !world.tiles[vec].isBlocked() &&
                                world.entities.filter { it.pos == vec && it != entity && it.isAlive() && !it.player }.isEmpty()
                    }
                },
                start = entity.pos,
                end = destination
        )


        if (path == null || path.size < 2)
            return Failed

        val next = world.tiles[path[1]]
        val entitiesOnNextTile = world.entities.filter { it.pos == path[1] }

        if (next.isBlocked() || entitiesOnNextTile.any { it.isAlive() }) {
            return Failed
        }


        // world.move(entity, path[1])

        val meleeTargets = entitiesOnNextTile.filter {
            it.isAlive() && it.player != entity.player
        }
        if (meleeTargets.isNotEmpty()) {
            return Melee(meleeTargets.first()).onPerform(world, entity)
        }

// TODO: add looting
//        // get next step, go there
//        if (entitiesOnNextTile.any { !it.isAlive() }) {
//            // Free loot pickup
//            return Loot().onPerform(world, entity)
//            //return Alternative(Loot())
//        }

        entity.pos = path[1]

        // if standing on loot box, loot next turn
        if (entity.pos == destination)
            return Succeeded
        else
            return InProgress

    }

}