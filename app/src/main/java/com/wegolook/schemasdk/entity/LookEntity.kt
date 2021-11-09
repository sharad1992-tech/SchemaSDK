package com.wegolook.schemasdk.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lookData")
data class LookEntity(
    @PrimaryKey(autoGenerate = true)
    var serialNumber: Int = 0,

    @ColumnInfo(name = "look_id")
    val lookId: Int,

    @ColumnInfo(name = "look_data")
    val lookData: String,

    @ColumnInfo(name = "Id")
    private var idAssignment: Int = 0,

    @ColumnInfo(name = "ImageArray")
    private val imageArray: ByteArray,

    @ColumnInfo(name = "Component")
    private val component: String? = null,

    @ColumnInfo(name = "ImageName")
    private val imageName: String? = null,

    @ColumnInfo(name = "Description")
    private val description: String? = null,
    @ColumnInfo(name = "Value")

    private var defaultValue: String? = null,
    @ColumnInfo(name = "FieldPos")
    private var fieldPos: Int = 0,

    @androidx.room.ColumnInfo(name = "ImageId")
    private val imageId: String? = null,

    @ColumnInfo(name = "JobId")
    private var jobId: Int = 0,

    @ColumnInfo(name = "GroupPos")
    private val groupPos: Int = 0,

    @ColumnInfo(name = "FilePath")
    private val filePath:String? = null
)
