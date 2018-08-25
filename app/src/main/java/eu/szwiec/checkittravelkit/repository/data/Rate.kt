package eu.szwiec.checkittravelkit.repository.data

data class Rate(
        val value: Float = 0.0f,
        val fromCurrencyCode: String = "",
        val lastUpdate: Long = 0
)