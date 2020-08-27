package net.bedev.notifweb.model

data class ModelPeriksa(
    val api_status: String,
    val `data`: List<Data>,
    val message: String
) {
    data class Data(
        val berat: String,
        val id_ibu: String,
        val id_periksa_balita: String,
        val jenis_kelamin: String,
        val nama: String,
        val rekom: String,
        val tgl: String,
        val tinggi: String,
        val umur: String
    )
}