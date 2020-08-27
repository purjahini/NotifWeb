package net.bedev.notifweb.model

data class balita(
    val `data`: List<Data>,
    val error: Boolean,
    val message: String
) {
    data class Data(
        val berat: String,
        val ibu: String,
        val id_balita: String,
        val jenis_kelamin: String,
        val nama: String,
        val rekom: String,
        val tgl: String,
        val tinggi: String,
        val umur: String
    )
}