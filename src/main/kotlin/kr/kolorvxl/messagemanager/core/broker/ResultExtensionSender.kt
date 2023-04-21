package kr.kolorvxl.messagemanager.core.broker

abstract class ResultExtensionSender<R, I>(val inner: I) : ResultSender<R>

abstract class NationalResultExtensionSender<R, L : Enum<L>, I>(inner: I)
    : NationalResultSender<R, L>, ResultExtensionSender<R, I>(inner)