package com.example.mvc.annotation

import com.example.mvc.validator.StringFormatDateTimeValidator
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.reflect.KClass

// 애노테이션을 적용 가능한 타겟
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME) // 애노테이션이 런타임까지 남아있음
@Constraint(validatedBy = [StringFormatDateTimeValidator::class]) // 적용할 validator
annotation class StringFormatDateTime(
    // 애노테이션 디폴트 프로퍼티
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],

    val pattern: String = "yyyy-MM-dd HH:mm:ss",
    val message: String = "시간 형식이 유효하지 않습니다."
)
