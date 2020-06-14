package mapgen.predicate

import math.Matrix2d

class CellEquals2d<T>(val tileType: T) : CellPredicate2d<T> {

    override fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean {
        return generatedMap2d.get(x, y) === tileType
    }


}