package mapgen.predicate

import math.Matrix2d
import kotlin.random.Random

class RandomPredicate2d<T>(val percetage: Float) : CellPredicate2d<T> {

    override fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean = Random.nextFloat() < percetage

}