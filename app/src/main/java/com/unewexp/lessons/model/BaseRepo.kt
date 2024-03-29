package com.unewexp.lessons.model

import com.google.gson.Gson
import com.unewexp.lessons.source.ErrorResponse
import com.unewexp.lessons.source.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCall: suspend () -> Response<T>): Resource<T> {
        return withContext(Dispatchers.IO){
            try {

                val response = apiToBeCall()

                if (response.isSuccessful){
                    Resource.Success(data = response.body()!!)

                }else{

                    val errorResponse: ErrorResponse? = convertErrorBody(response.errorBody())

                    Resource.Error(errorMessage = (errorResponse?.cod + " " + errorResponse?.message)
                        ?: "smt went wrong"
                    )
                }
            }catch (e: HttpException){
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            }catch (e: IOException){
                Resource.Error("Please check your network connection")
            }catch (e: Exception){
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse?{
        return try {
            Gson().fromJson(errorBody?.charStream(), ErrorResponse::class.java)
        }catch (e: Exception){
            null
        }
    }
}