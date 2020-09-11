package net.dev.weatherapp.repo.local.model

data class NewsResponse (
    var status : String,
    var totalResults : Int,
    var articles : List<News>
)
