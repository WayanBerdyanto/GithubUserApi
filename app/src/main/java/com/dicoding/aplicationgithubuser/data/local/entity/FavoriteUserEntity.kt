package com.dicoding.aplicationgithubuser.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favoriteUser")
@Parcelize
class FavoriteUserEntity(

    @field:ColumnInfo(name = "username")
    @PrimaryKey
    var username: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,
) : Parcelable