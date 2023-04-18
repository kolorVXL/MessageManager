package kr.kolorvxl.messagemanager.core

sealed interface Message

object UnloadableMessage : Message

data class LoadedMessage(val value: String?) : Message