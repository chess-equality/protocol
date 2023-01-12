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
package spp.protocol.artifact.trace

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * todo: description.
 *
 * @since 0.1.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class TraceSpanRef(
    val traceId: String,
    val parentSegmentId: String,
    val parentSpanId: Int,
    val type: String
) {
    constructor(json: JsonObject) : this(
        traceId = json.getString("traceId"),
        parentSegmentId = json.getString("parentSegmentId"),
        parentSpanId = json.getInteger("parentSpanId"),
        type = json.getString("type")
    )

    fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }
}
