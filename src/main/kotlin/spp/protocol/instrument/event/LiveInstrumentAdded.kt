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
package spp.protocol.instrument.event

import io.vertx.codegen.annotations.DataObject
import io.vertx.core.json.JsonObject
import spp.protocol.instrument.LiveInstrument
import spp.protocol.instrument.LiveInstrumentType
import java.time.Instant

/**
 * todo: description.
 *
 * @since 0.7.7
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@DataObject
data class LiveInstrumentAdded(
    val instrument: LiveInstrument,
    override val occurredAt: Instant = Instant.now()
) : LiveInstrumentEvent {
    override val eventType: LiveInstrumentEventType
        get() {
            return when (instrument.type) {
                LiveInstrumentType.BREAKPOINT -> LiveInstrumentEventType.BREAKPOINT_ADDED
                LiveInstrumentType.LOG -> LiveInstrumentEventType.LOG_ADDED
                LiveInstrumentType.METER -> LiveInstrumentEventType.METER_ADDED
                LiveInstrumentType.SPAN -> LiveInstrumentEventType.SPAN_ADDED
            }
        }

    constructor(json: JsonObject) : this(
        instrument = LiveInstrument.fromJson(json.getJsonObject("instrument")),
        occurredAt = Instant.parse(json.getString("occurredAt"))
    )

    override fun toJson(): JsonObject {
        return JsonObject.mapFrom(this)
    }
}
