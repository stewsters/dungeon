package math


class Matrix2d<T>(val xSize: Int, val ySize: Int, val data: Array<T>) {

    operator fun get(p: Vec2): T = get(p.x, p.y)

    operator fun get(x: Int, y: Int): T {
        return data[x + y * xSize]
    }

    operator fun set(p: Vec2, value: T) = set(p.x, p.y, value)

    operator fun set(x: Int, y: Int, value: T) {
        data[x + y * xSize] = value
    }

    fun contains(p: Vec2): Boolean = contains(p.x, p.y)

    fun contains(x: Int, y: Int): Boolean = !outside(x, y)

    fun outside(p: Vec2): Boolean = outside(p.x, p.y)

    fun outside(x: Int, y: Int): Boolean = (x < 0 || y < 0 || x >= xSize || y >= ySize)

    inline fun forEach(function: (T) -> Unit) = data.forEach(function)

    inline fun forEach(func: (x: Int, y: Int) -> Unit) {
        for (x in 0 until xSize) {
            for (y in 0 until ySize) {
                func(x, y)
            }
        }
    }

    inline fun forEachIndexed(function: (Int, Int, T) -> Unit) = data.forEachIndexed { i, t ->
        function(
                i % xSize,
                i / xSize,
                t
        )
    }

    fun getSize() = Vec2[xSize, ySize]

    fun setFromList(list: List<T>) {
        list.forEachIndexed { index, t -> data[index] = t }
    }

    fun <R : Comparable<R>> sortedBy(function: (T) -> R?): List<T> = data.sortedBy(function)

    inline fun <reified R> map(transform: (T) -> R): Matrix2d<R> = Matrix2d(xSize, ySize, data.map(transform))

//    fun <R> mapIndexed(transform: (Int, Int, T) -> R): Matrix2d<R> = Matrix2d.fromArray(xSize, ySize,
//            data.mapIndexed { i, t ->
//                transform(
//                        i % xSize,
//                        i / xSize,
//                        t
//                )
//            }
//    )

}

inline fun <reified T> Matrix2d(size: Vec2, init: (Int, Int) -> T) = Matrix2d(size.x, size.y, init)

inline fun <reified T> Matrix2d(xSize: Int, ySize: Int, init: (Int, Int) -> T) =
        Matrix2d(xSize, ySize, Array(xSize * ySize) { i -> init(i % xSize, i / xSize) })

inline fun <reified T> Matrix2d(xSize: Int, ySize: Int, dataList: List<T>): Matrix2d<T> =
        Matrix2d<T>(xSize, ySize, Array<T>(dataList.size) { i -> dataList[i] })
