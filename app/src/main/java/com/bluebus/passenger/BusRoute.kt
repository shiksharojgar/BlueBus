
package com.bluebus.passenger

data class BusRoute(
    val routeId: String,
    val busName: String,
    val operatorName: String,
    val startPoint: String,
    val endPoint: String,
    val stops: List<String>,
    val departureTime: String?,
    val arrivalTime: String?,
    val fare: String?,
    val isVerified: Boolean,
    val source: String,
    val lastVerifiedDate: String?
)
