package eu.szwiec.checkittravelkit

import eu.szwiec.checkittravelkit.repository.data.*

val poland = Country(
        id = "PL",
        name = "Poland",
        timezone = "Europe/Warsaw",
        tapWater = "safe",
        vaccinations = mapOf("Hepatitis B" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"),
        imageUrl = "https://www.dropbox.com/s/5b06v8pgg4ifxy0/poland.jpg?dl=1",
        visa = Visa("Not required"),
        electricity = Electricity(
                voltage = "230",
                frequency = "50",
                plugs = listOf("C", "E")
        ),
        telephones = Telephones(
                policeNumber = "112",
                ambulanceNumber = "112",
                prefix = "48"
        ),
        currency = Currency(
                code = "PLN",
                name = "Polish zloty",
                symbol = "zł",
                rate = Rate(0.2)
        )
)

val thailand = Country(
        id = "TH",
        name = "Thailand",
        timezone = "Asia/Bangkok",
        tapWater = "not safe",
        vaccinations = mapOf(
                "Malaria" to "Malaria occurs in some areas. Protect yourself from mosquito bites. Precautions are personal. Consult a qualified medical professional to determine the right actions",
                "Hepatitis A" to "Vaccination is recommended for all travelers to this country",
                "Typhoid" to "The vaccination advice is personal. Consult a qualified medical professional to determine whether vaccination is useful for you"
        ),
        imageUrl = "https://www.dropbox.com/s/jxq8jpb9xdvwq3z/thailand.jpg?dl=1",
        visa = Visa("Required"),
        electricity = Electricity(
                voltage = "230",
                frequency = "50",
                plugs = listOf("A", "B", "C")
        ),
        telephones = Telephones(
                policeNumber = "191",
                ambulanceNumber = "1669",
                prefix = "66"
        ),
        currency = Currency(
                code = "THB",
                name = "Thai baht",
                symbol = "฿",
                rate = Rate(0.2)
        )
)