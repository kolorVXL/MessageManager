package kr.kolorvxl.messagemanager.core.broker

interface ExtensionSender<R, I> : ResultSender<R> {
    val inner: I
}

interface NationalExtensionSender<R, L : Enum<L>, I>
    : NationalResultSender<R, L>, ExtensionSender<R, I>