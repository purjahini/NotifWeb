package net.bedev.notifweb.model

data class user(
    val `data`: List<Data>,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val email: String,
        val foto_ibu: String,
        val id_ibu: String,
        val nama: String,
        val no_hp: String,
        val password: String
    )
}