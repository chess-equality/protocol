/*
 * Source++, the continuous feedback platform for developers.
 * Copyright (C) 2022-2023 CodeBrig, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package spp.protocol.view

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.ArtifactQualifiedName
import spp.protocol.instrument.location.LiveSourceLocation
import spp.protocol.service.SourceServices.Subscribe.toLiveViewSubscription

/**
 * A back-end subscription to events/metrics for a given set of entities.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveView(
    val subscriptionId: String? = null, //todo: actual bottom
    val entityIds: MutableSet<String>,
    val artifactQualifiedName: ArtifactQualifiedName? = null, //todo: remove, use artifactLocation
    val artifactLocation: LiveSourceLocation? = null, //todo: bottom?
    val viewConfig: LiveViewConfig
) {
    constructor(json: JsonObject) : this(
        subscriptionId = json.getString("subscriptionId"),
        entityIds = json.getJsonArray("entityIds").map { it.toString() }.toMutableSet(),
        artifactQualifiedName = json.getJsonObject("artifactQualifiedName")?.let { ArtifactQualifiedName(it) },
        artifactLocation = json.getJsonObject("artifactLocation")?.let { LiveSourceLocation(it) },
        viewConfig = LiveViewConfig(json.getJsonObject("viewConfig"))
    )

    fun toJson(): JsonObject {
        val json = JsonObject()
        json.put("subscriptionId", subscriptionId)
        json.put("entityIds", JsonArray().apply { entityIds.forEach { add(it) } })
        artifactQualifiedName?.let { json.put("artifactQualifiedName", it.toJson()) }
        artifactLocation?.let { json.put("artifactLocation", it.toJson()) }
        json.put("viewConfig", viewConfig.toJson())
        return json
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as LiveView
        if (subscriptionId != other.subscriptionId) return false
        return true
    }

    override fun hashCode(): Int {
        return subscriptionId?.hashCode() ?: 0
    }

    override fun toString(): String {
        return buildString {
            append("LiveView(")
            if (subscriptionId != null) append("subscriptionId=$subscriptionId, ")
            append("entityIds=$entityIds, ")
            if (artifactQualifiedName != null) append("artifactQualifiedName=$artifactQualifiedName, ")
            if (artifactLocation != null) append("artifactLocation=$artifactLocation, ")
            append("viewConfig=$viewConfig")
            append(")")
        }
    }

    fun addEventListener(vertx: Vertx, listener: (LiveViewEvent) -> Unit) {
        val viewId = subscriptionId ?: error("Cannot add event listener to view with null subscriptionId")
        vertx.eventBus().consumer<JsonObject>(toLiveViewSubscription(viewId)).handler {
            listener.invoke(LiveViewEvent(it.body()))
        }
    }
}
