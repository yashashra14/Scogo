package com.example.scogo.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class NetworkCall {

    suspend fun <T> sendRequest(request: suspend () -> T): Flow<Resource<T>> {
        return flow {
                emit(Resource.Loading)
                val response = request.invoke()
                emit(Resource.Success(response))
        }.catch { exception ->
            when (exception) {
                is HttpException -> {
                    emit(
                        Resource.Error(
                            error = exception,
                            errorCode = exception.response()?.code(),
                            errorBody = exception.response()?.errorBody()?.string()
                        ))
                }

                else -> {
                    emit(Resource.Error(exception))
                }
            }
        }.flowOn(Dispatchers.IO)
    }
}