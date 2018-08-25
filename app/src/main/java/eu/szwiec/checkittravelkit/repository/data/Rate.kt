package eu.szwiec.checkittravelkit.repository.data

data class Rate(
        val value: Double = 0.0,
        val fromCurrencyCode: String = "",
        val lastUpdate: Long = 0
)