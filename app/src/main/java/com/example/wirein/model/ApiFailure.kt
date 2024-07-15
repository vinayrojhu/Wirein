package com.example.wirein.model

sealed class ApiFailure {
    data class Unknown(val error: String) : ApiFailure()
}