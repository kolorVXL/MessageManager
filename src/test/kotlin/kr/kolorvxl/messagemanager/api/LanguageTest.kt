package kr.kolorvxl.messagemanager.api

import kr.kolorvxl.messagemanager.language.*
import kr.kolorvxl.messagemanager.message.MessageSet
import kr.kolorvxl.messagemanager.util.kebabCase
import kr.kolorvxl.messagemanager.util.simpleReduce
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals


val testLangSet1 = StaticLanguageSet(Sealed, SealedLanguageSet::class, String::kebabCase)

sealed interface SealedLanguageSet

object KR : SealedLanguageSet, StaticLanguage()

object JP : SealedLanguageSet, StaticLanguage()

object EN : SealedLanguageSet, StaticLanguage()



val testLangSet2 = StaticLanguageSet(Nested, NestedLanguageSet::class, String::kebabCase)

object NestedLanguageSet {

    object KR : StaticLanguage()

    object JP : StaticLanguage()

    object EN : StaticLanguage()

}


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LanguageTest {

    @ParameterizedTest
    @MethodSource("typeSets")
    fun `static language test`(set: LanguageSet) {
        set.languages
            .map { it.name }
            .forEach(::println)

        assertEquals(3, set.languages.size)
    }

    private fun typeSets(): List<LanguageSet> = listOf(testLangSet1, testLangSet2)

}