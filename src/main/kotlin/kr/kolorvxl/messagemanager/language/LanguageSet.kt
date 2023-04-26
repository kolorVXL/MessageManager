package kr.kolorvxl.messagemanager.language


interface Language {
    val name: String
    val id: Int
}

class LanguageImpl(
    override val name: String,
    override val id: Int
) : Language

interface LanguageSet {
    val languages: List<Language>
}