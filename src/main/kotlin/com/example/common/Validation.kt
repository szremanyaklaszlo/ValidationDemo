package com.example.common


class ValidationContext(val message: String) {

    private val _errors = mutableListOf<String>()
    val errors: List<String> get() = _errors

    fun add(errorMessage: String) {
        _errors.add(errorMessage)
    }

    fun addIf(condition: Boolean, errorMessage: String) {
        if (condition) _errors.add(errorMessage)
    }

    fun throwIfAny() {
        if (_errors.any()) {
            throw InputValidationException(message, _errors.toList())
        }
    }
}

inline fun validate(
    message: String = "Validation has been failed.",
    conditions: ValidationContext.() -> Unit
) {
    val context = ValidationContext(message)
    context.conditions()
    context.throwIfAny()
}

class InputValidationException(message: String, val errors: List<String>) : RuntimeException(message)
