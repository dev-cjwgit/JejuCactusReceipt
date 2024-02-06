package com.cjwgit.jejucactusreceipt.exec

enum class ErrorMessage(val code: Int, val message: String) {
    NOT_SELECT_ITEM(100_000_000, "항목을 선택해 주세요."),
    NEED_INPUT_AMOUNT(100_000_001, "수량을 입력해 주세요"),
    EXCEED_ITEM_COUNT(100_000_002, "더 이상 담을 수 없습니다.")
}