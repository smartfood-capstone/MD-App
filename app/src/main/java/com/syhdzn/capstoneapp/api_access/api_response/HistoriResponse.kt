package com.syhdzn.capstoneapp.api_access.api_response

data class HistoriResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Int
)