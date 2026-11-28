
package com.bluebus.passenger

import com.google.firebase.firestore.FirebaseFirestore

object SponsoredAdRepository {

    private val db = FirebaseFirestore.getInstance()

    fun loadApprovedAds(
        placement: String,
        onSuccess: (List<SponsoredAd>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("sponsored_ads")
            .whereEqualTo("status", "approved")
            .whereEqualTo("paymentVerified", true)
            .get()
            .addOnSuccessListener { snapshot ->

                val currentTime = System.currentTimeMillis()

                val ads = snapshot.documents.mapNotNull { document ->
                    document.toObject(SponsoredAd::class.java)
                        ?.copy(adId = document.id)
                }.filter { ad ->

                    ad.placements.contains(placement) &&
                    ad.startAtMillis <= currentTime &&
                    ad.endAtMillis > currentTime
                }

                onSuccess(ads)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}
