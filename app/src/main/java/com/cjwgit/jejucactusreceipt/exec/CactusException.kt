package com.cjwgit.jejucactusreceipt.exec

class CactusException(
    val errorMessage: ErrorMessage
) : RuntimeException(errorMessage.message)