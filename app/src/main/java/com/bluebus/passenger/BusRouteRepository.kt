
package com.bluebus.passenger

object BusRouteRepository {

    // वास्तविक बसों का सत्यापित डेटा बाद में यहाँ जोड़ा जाएगा।
    private val routes = emptyList<BusRoute>()

    fun findRoutes(from: String, to: String): List<BusRoute> {
        val start = from.trim()
        val destination = to.trim()

        if (start.isEmpty() || destination.isEmpty()) {
            return emptyList()
        }

        return routes.filter { route ->
            val fromIndex = route.stops.indexOfFirst {
                it.equals(start, ignoreCase = true)
            }

            val toIndex = route.stops.indexOfFirst {
                it.equals(destination, ignoreCase = true)
            }

            fromIndex >= 0 && toIndex > fromIndex
        }
    }
}
