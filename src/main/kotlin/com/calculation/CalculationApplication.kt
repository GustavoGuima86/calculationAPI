package com.calculation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan("com.calculation")
class SumApplication

fun main(args: Array<String>) {
	runApplication<SumApplication>(*args)
}
