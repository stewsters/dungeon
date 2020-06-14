package mapgen.predicate

import math.Matrix2d

class NotPredicate2d<T>(private val predicate: CellPredicate2d<T>) : CellPredicate2d<T> {
    override fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean {
        return !predicate.belongs(generatedMap2d, x, y)
    }

}