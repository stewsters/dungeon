package math

fun findPath2d(
        size: Vec2,
        cost: (Vec2) -> Double,
        heuristic: (Vec2, Vec2) -> Double,
        neighbors: (Vec2) -> List<Vec2>,
        start: Vec2,
        end: Vec2
): List<Vec2>? {

    val costs = Matrix2d(size.x, size.y) { _, _ -> Double.MAX_VALUE }
    val parent = Matrix2d<Vec2?>(size.x, size.y) { _, _ -> null }
    val fScore = Matrix2d(size.x, size.y) { _, _ -> Double.MAX_VALUE }

    val openSet = mutableListOf<Vec2>()
    val closeSet = HashSet<Vec2>()

    openSet.add(start)
    costs[start] = 0.0
    fScore[start] = heuristic(start, end)

    while (openSet.isNotEmpty()) {

        // Grab the next node with the lowest cost
        val cheapestNode: Vec2 = openSet.minBy { fScore[it] }!!

        if (cheapestNode == end) {
            // target found, we have a path
            val path = mutableListOf(cheapestNode)

            var node = cheapestNode
            while (parent[node] != null) {
                node = parent[node]!!
                path.add(node)
            }
            return path.reversed()
        }

        openSet.remove(cheapestNode)
        closeSet.add(cheapestNode)

        // get the neighbors
        //  for each point, set the cost, and a pointer back if we set the cost

        for (it in neighbors(cheapestNode)) {
            if (it.x < 0 || it.y < 0 || it.x >= size.x || it.y >= size.y)
                continue

            if (closeSet.contains(it))
                continue

            val nextCost = costs[cheapestNode] + cost(it)

            if (nextCost < costs[it]) {
                costs[it] = nextCost
                fScore[it] = nextCost + heuristic(it, end)
                parent[it] = cheapestNode


                if (closeSet.contains(it)) {
                    closeSet.remove(it)
                }
                openSet.add(it)
            }
        }
    }

    // could not find a path
    return null

}
