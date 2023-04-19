package kr.kolorvxl.messagemanager.core.broker

interface ResultSender<R> {

    fun sendResult(result: R)

}


interface NationalResultSender<R, L : Enum<L>> : ResultSender<R> {

    val language: L

}