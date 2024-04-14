package org.itmo.itmoevent.view.auth.service.models
import org.itmo.itmoevent.view.auth.service.api.LoginApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object LoginRetrofitClient {
    private const val BASE_URL = "https://2d580783-c03b-4a2b-b58f-5ae1c1b2652f.mock.pstmn.io/"

    val loginService: LoginApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        retrofit.create(LoginApi::class.java)
    }
}