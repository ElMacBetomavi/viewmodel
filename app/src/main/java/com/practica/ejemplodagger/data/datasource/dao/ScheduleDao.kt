package com.practica.ejemplodagger.data.datasource.dao

import androidx.room.*
import com.practica.ejemplodagger.data.entities.ScheduleEntity

@Dao
interface ScheduleDao {

    @Query("SELECT * FROM schedule_entity ORDER BY date DESC")
    fun getSchedule(): MutableList<ScheduleEntity>

    @Query("SELECT * FROM schedule_entity WHERE id IN (:scheduleIds)")
    fun getById(scheduleIds: IntArray): MutableList<ScheduleEntity>

    @Insert
    fun addSchedule(schedule: ScheduleEntity):Long

    @Delete
    fun deleteSchedule(schedule: ScheduleEntity)

    @Update
    suspend fun updateSchedule(schedule: ScheduleEntity): Int


}