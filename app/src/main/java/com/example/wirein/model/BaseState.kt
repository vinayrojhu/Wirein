package com.example.wirein.model

sealed class BaseState<out DATA : Any, out ERROR : Any> {
    /**
     * [Loading] state represents the initial state before success or failure of the async work
     */
    object Loading : BaseState<Nothing, Nothing>()

    /**
     * [Success] Represents the success state
     * @param data represents success of the async work. It can be Unit as well
     */
    data class Success<out DATA : Any>(val data: DATA) : BaseState<DATA, Nothing>()

    /**
     * [Failed] state represents the failure of the async work. Use any error model to represent the error
     */
    data class Failed<out ERROR : Any>(val error: ERROR) : BaseState<Nothing, ERROR>()
}