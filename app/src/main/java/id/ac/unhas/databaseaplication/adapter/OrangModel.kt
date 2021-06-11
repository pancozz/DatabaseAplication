package id.ac.unhas.databaseaplication.adapter

import java.util.*

data class OrangModel(
    val id: Int = getAutoId(),
    var nama: String = "",
    var email: String = ""

) {
    companion object {
        fun getAutoId(): Int {
            val random = Random()
            return random.nextInt(100)
        }
    }
}