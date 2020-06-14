package mapgen.predicate

import math.Matrix2d

class CellEqualAny2d<T>(val tileTypes: List<T>) : CellPredicate2d<T> {

    override fun belongs(generatedMap2d: Matrix2d<T>, x: Int, y: Int): Boolean {
        return tileTypes.contains(generatedMap2d.get(x, y))
    }

}