package com.example.cryptowallet.app.core.domain

import com.example.cryptowallet.app.core.domain.Error

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Failure<out E: Error>(val error: E) : Result<Nothing, E>

}

inline fun<T, R, E: Error> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Failure -> Result.Failure(error)
    }
}

fun <T,E:Error> Result<T,E>.asEmptyDataResult(): EmptyResult<E>{
    return map {}
}

inline fun <T,E:Error> Result<T,E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this){
        is Result.Failure -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}


typealias EmptyResult<E> = Result<Unit, E>


