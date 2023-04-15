package kr.kolorvxl.messagemanager.core.typeset

open class DynamicMessageTypeSet(function: DynamicMessageTypeSet.() -> Unit) : MessageTypeSet {

    override val messageTypes: List<MessageType> by lazy {
        plainMessageTypes.mapIndexed { index, strings -> MessageType(strings, index) }
    }


    private val values: ArrayList<List<String>> = ArrayList()

    private val plainMessageTypes: List<List<String>> by lazy {
        function()
        values.toList()
    }


    fun undef(name: String) = Unit

    fun undef(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        associate(
            DynamicMessageTypeSet(function)
                .plainMessageTypes
                .map { listOf(name) + it }
        )
    }

    fun def(name: String) {
        associate(name)
    }

    fun def(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        undef(name, function)
        def(name)
    }

    private fun associate(name: String) = values.add(listOf(name))

    private fun associate(names: List<List<String>>) = values.addAll(names)

}