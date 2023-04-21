package kr.kolorvxl.messagemanager.core.message

sealed interface Message

object UnloadableMessage : Message

data class LoadedMessage(val value: String?) : Message