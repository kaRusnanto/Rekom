package com.to.reqom

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
data class Movie(
    val id: Int,
    val title: String,
    val poster_path: String,
) : Serializable
data class MovieDetails(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("belongs_to_collection")
    val collection: Collection?,
    val budget: Long?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Long?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany>?,
    val productionCountries: List<ProductionCountry>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val revenue: Long?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)

data class Collection(
    val id: Long?,
    val name: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?
)

data class Genre(
    val id: Int?,
    val name: String?
)

data class ProductionCompany(
    val id: Long?,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: String?
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String?,
    val name: String?
)

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String?,
    @SerializedName("iso_639_1")
    val iso6391: String?,
    val name: String?
)
