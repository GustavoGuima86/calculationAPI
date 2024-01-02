package com.calculation.adapter.database

import com.calculation.adapter.database.entities.MathEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MathJpaRepository: JpaRepository<MathEntity, Long> {

    fun save(sum: MathEntity): MathEntity
    override fun findById(id: Long): Optional<MathEntity>
}