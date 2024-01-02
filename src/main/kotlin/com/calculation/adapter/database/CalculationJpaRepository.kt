package com.calculation.adapter.database

import com.calculation.adapter.database.entities.CalculationEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CalculationJpaRepository: JpaRepository<CalculationEntity, Long> {

    fun save(sum: CalculationEntity): CalculationEntity
    override fun findById(id: Long): Optional<CalculationEntity>
}