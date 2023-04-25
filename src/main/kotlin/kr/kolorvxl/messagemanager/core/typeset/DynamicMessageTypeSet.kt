package kr.kolorvxl.messagemanager.core.typeset

open class DynamicMessageTypeSet(val function: DynamicMessageTypeSet.() -> Unit) : MessageTypeSet {

    /**
     * The property for transforming [function] to [List]<[MessageType]>.
     * @return Converted [List]<[MessageType]>.
     */
    override val messageTypes: List<MessageType> by lazy {
        plainMessageTypes.mapIndexed { index, strings -> MessageType(strings, index) }
    }

    open fun undef(name: String) {
        // Empty (Ha Ha)
    }

    open fun undef(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        associate(name, function)
    }

    open fun def(name: String) {
        associate(name)
    }

    open fun def(name: String, function: DynamicMessageTypeSet.() -> Unit) {
        associate(name)
        associate(name, function)
    }


    /**
     * The property for providing plaintext message types.
     * This property and a result of transforming [messageTypes] are same thing.
     * @return plaintext message types
     */
    private val plainMessageTypes: List<List<String>> by lazy {
        function()
        values.toList()
    }

    private val values: ArrayList<List<String>> = ArrayList()

    private fun associate(name: String) = values.add(listOf(name))

    private fun associate(firstName: String, function: DynamicMessageTypeSet.() -> Unit) {
        DynamicMessageTypeSet(function)
            .plainMessageTypes
            .map { listOf(firstName) + it }
            .forEach { values.add(it) }
    }

}