package net.bedev.notifweb.model

data class ModelBalita(
    val api_status: String,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val doa: String,
        val foto_balita: String,
        val ibu: String,
        val id_balita: String,
        val nama_balita: String
    )
}