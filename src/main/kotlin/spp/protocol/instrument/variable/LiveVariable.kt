/*
 * Source++, the open-source live coding platform.
 * Copyright (C) 2022 CodeBrig, Inc.
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
package spp.protocol.instrument.variable

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject

/**
 * Holds the serialized state of a variable encountered in a live probe.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveVariable(
    val name: String,
    val value: Any?,
    val lineNumber: Int = -1,
    val scope: LiveVariableScope? = null,
    val liveClazz: String? = null,
    val liveIdentity: String? = null,
    val presentation: String? = null
) {
    constructor(json: JsonObject) : this(
        json.getString("name"),
        json.getValue("value"),
        json.getInteger("lineNumber"),
        json.getString("scope")?.let { LiveVariableScope.valueOf(it) },
        json.getString("liveClazz"),
        json.getString("liveIdentity"),
        json.getString("presentation")
    )
}
