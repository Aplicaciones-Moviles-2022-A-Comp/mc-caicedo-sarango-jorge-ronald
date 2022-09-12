package com.example.app_farmacia.promoadapter

import com.example.app_farmacia.Promocion

class PromoProvider {
    companion object{
        val promoList = arrayListOf<Promocion>(
            Promocion("Promo 1",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
            "https://clinicasanvicente.med.ec/wp-content/uploads/2017/02/Promociones2-960x720.png"),
            Promocion("Promo 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                "https://pbs.twimg.com/media/EHp5sPDX4AQQsty.jpg"),
            Promocion("Promo 3",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
                "https://pbs.twimg.com/media/BcgOsa7CcAIX_a3.jpg")
        )
    }
}