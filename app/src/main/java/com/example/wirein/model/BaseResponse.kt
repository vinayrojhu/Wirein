package com.example.wirein.model

data class BaseResponse<T>(
    val type: String,
    val data: List<T>,
)