package com.practica.ejemplodagger.data.repository

import com.practica.ejemplodagger.MainApplication
import com.practica.ejemplodagger.data.entities.ScheduleEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ScheduleRepository {
    private val db = MainApplication.database.scheduleDao()

    suspend fun getAll(): MutableList<ScheduleEntity> =
        withContext(Dispatchers.IO) {
            db.getSchedule()
        }

    suspend fun add(schedule: ScheduleEntity)=
        withContext(Dispatchers.IO) {
            db.addSchedule(schedule)
        }
    suspend fun delete(schedule: ScheduleEntity)=
        withContext(Dispatchers.IO) {
            db.deleteSchedule(schedule)
        }

    suspend fun update(schedule: ScheduleEntity)=
        withContext(Dispatchers.IO) {
            db.updateSchedule(schedule)
        }
}