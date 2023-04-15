package kr.kolorvxl.messagemanager.core

sealed interface Message

object NotInitMessage : Message

object NullMessage : Message

data class ValuableMessage(val value: String) : Message