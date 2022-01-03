package spp.protocol.instrument

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@Serializable
data class LiveSourceLocation @JvmOverloads constructor(
    val source: String,
    val line: Int,
    val service: String? = null,
    val serviceInstance: String? = null, //todo: fully impl
    val commitId: String? = null, //todo: impl
    val fileChecksum: String? = null //todo: impl
) : Comparable<LiveSourceLocation> {

    override fun compareTo(other: LiveSourceLocation): Int {
        val sourceCompare = source.compareTo(other.source)
        if (sourceCompare != 0) return sourceCompare
        return line.compareTo(other.line)
    }
}
