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
package spp.protocol.artifact.log

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.2.1
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LogCountSummary(
    val timestamp: Instant,
    val logCounts: Map<String, Int>
) {
    constructor(json: JsonObject) : this(
        timestamp = Instant.parse(json.getString("timestamp")),
        logCounts = json.getJsonObject("logCounts").associate { it.key.toString() to it.value as Int }
    )
}