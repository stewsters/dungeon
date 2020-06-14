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
            return Walk(destination - entity.pos).onPerform(world, entity)
        }

        // if not adjacent, path to point
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

        val offset = path[1] - entity.pos

        return Walk(offset).onPerform(world, entity)
    }

}