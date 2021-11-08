package spp.protocol.advice.informative

import spp.protocol.advice.AdviceCategory
import spp.protocol.advice.AdviceType
import spp.protocol.advice.ArtifactAdvice
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.artifact.exception.LiveStackTrace
import kotlinx.datetime.Instant

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
class ActiveExceptionAdvice(
    artifact: ArtifactQualifiedName,
    stackTrace: LiveStackTrace,
    occurredAt: Instant
) : ArtifactAdvice(artifact, AdviceCategory.INFORMATIVE, AdviceType.ActiveExceptionAdvice) {

    var stackTrace: LiveStackTrace = stackTrace
        private set
    var occurredAt: Instant = occurredAt
        private set

    fun update(stackTrace: LiveStackTrace, occurredAt: Instant) {
        this.stackTrace = stackTrace
        this.occurredAt = occurredAt
        triggerUpdated()
    }

    /**
     * Compares everything except [LiveStackTrace.message] as the message may just contain timestamp differences
     * even though everything else is equivalent.
     */
    override fun isSameArtifactAdvice(artifactAdvice: ArtifactAdvice): Boolean {
        return artifactAdvice is ActiveExceptionAdvice && artifactAdvice.artifact == artifact &&
                artifactAdvice.stackTrace.exceptionType == stackTrace.exceptionType &&
                artifactAdvice.stackTrace.elements == stackTrace.elements &&
                artifactAdvice.stackTrace.causedBy == stackTrace.causedBy
    }

    override fun updateArtifactAdvice(artifactAdvice: ArtifactAdvice) {
        update((artifactAdvice as ActiveExceptionAdvice).stackTrace, artifactAdvice.occurredAt)
    }

    override fun toString(): String {
        return "$type{$artifact - ${stackTrace.exceptionType} @ $occurredAt}"
    }
}