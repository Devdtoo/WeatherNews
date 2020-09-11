package net.dev.weatherapp.repo.local.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    var description: String,
    var icon: String,
    var id: Int,
    var main: String
) : Parcelable