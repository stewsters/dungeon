package game.ai


import east
import game.action.Action
import game.action.Walk
import game.action.WalkToPoint
import game.world.Entity
import game.world.World
import math.Vec2
import math.getEuclideanDistance
import north
import south
import west


interface AI {
    // receives instructions
    fun setAction(action: Action?)

    // acts on them
    fun getNextAction(entity: Entity, world: World): Action?

    fun highlight(worldPos: Vec2): Boolean = false

    fun nextTurn(): Long
    fun delayTurn(time: Long)
}

abstract class BaseAI : AI {
    private var turn: Long = 0
    override fun nextTurn(): Long = turn
    override fun delayTurn(time: Long) {
        turn += time
    }
}

class PlayerAI : BaseAI() {

    private var action: Action? = null

    override fun getNextAction(entity: Entity, world: World): Action? {
        return action
    }

    override fun setAction(action: Action?) {
        this.action = action
    }

    override fun highlight(worldPos: Vec2): Boolean {
        if (action is WalkToPoint)
            return (action as WalkToPoint).destination == worldPos
        return false
    }

}

class OpponentAI : BaseAI() {
    override fun setAction(action: Action?) {

    }

    override fun getNextAction(entity: Entity, world: World): Action {

        // see enemy
        val dist = getEuclideanDistance(world.player.pos, entity.pos)
        if (dist < 10) { //charge
            return WalkToPoint(world.player.pos)
        } else {
            return Walk(listOf(east, west, north, south).random())
        }

    }

}