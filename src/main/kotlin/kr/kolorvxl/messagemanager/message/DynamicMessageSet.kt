package kr.kolorvxl.messagemanager.message

open class DynamicMessageSet(val commands: DynamicMessageSet.() -> Unit) : MessageSet {

    /**
     * The property for transforming [commands] to [List]<[Message]>.
     * @return Converted [List]<[Message]>.
     */
    override val messages: List<Message> by lazy {
        plainMessageTypes.mapIndexed { index, strings -> MessageImpl(strings, index) }
    }

    open fun undef(name: String) {
        // Empty (Ha Ha)
    }

    open fun undef(name: String, function: DynamicMessageSet.() -> Unit) {
        associate(name, function)
    }

    open fun def(name: String) {
        associate(name)
    }

    open fun def(name: String, function: DynamicMessageSet.() -> Unit) {
        associate(name)
        associate(name, function)
    }



    private val plainMessageTypes: List<List<String>> by lazy {
        commands()
        values.toList()
    }

    private val values: ArrayList<List<String>> = ArrayList()

    private fun associate(name: String) = values.add(listOf(name))

    private fun associate(firstName: String, function: DynamicMessageSet.() -> Unit) {
        DynamicMessageSet(function)
            .plainMessageTypes
            .map { listOf(firstName) + it }
            .forEach { values.add(it) }
    }

}