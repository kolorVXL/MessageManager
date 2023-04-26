package kr.kolorvxl.messagemanager.language

import kotlin.reflect.KClass

class StaticLanguageSet(
    private val languageSetType: LanguageSetType,
    private val languageClass: KClass<*>,
    private val transform: String.() -> String = { this }
) : LanguageSet {

    override val languages: List<Language> by lazy {
        when (languageSetType) {
            is Sealed -> languageClass.sealedSubclasses
            is Nested -> languageClass.nestedClasses
        }.staticLanguages.mapIndexed { index, value ->
            val language = LanguageImpl(value.name.transform(), index)
            value.language.language = language
            language
        }
    }

}


sealed interface LanguageSetType

object Sealed : LanguageSetType

object Nested : LanguageSetType


abstract class StaticLanguage(var language: Language? = null) : Language {

    override val name: String
        get() = language!!.name

    override val id: Int
        get() = language!!.id
}


private class NamedStaticLanguage(val name: String, val language: StaticLanguage)

private val Collection<KClass<*>>.staticLanguages: List<NamedStaticLanguage>
    get() = this
        .filter { it.simpleName != null }
        .mapNotNull { it.objectInstance }
        .filterIsInstance<StaticLanguage>()
        .map { NamedStaticLanguage(it.className, it) }

private val Any.className: String get() = this::class.simpleName!!