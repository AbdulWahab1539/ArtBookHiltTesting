package com.abduldev.artbookapptestingguide.api.models

data class ImageResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)