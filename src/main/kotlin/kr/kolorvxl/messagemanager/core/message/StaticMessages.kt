package kr.kolorvxl.messagemanager.core.message

/**
 * The message set interface. You can include [StaticMessage] in this.
 */
interface StaticMessageSet

/**
 * The single message class.
 */
abstract class StaticMessage(var identifier: Int? = null)