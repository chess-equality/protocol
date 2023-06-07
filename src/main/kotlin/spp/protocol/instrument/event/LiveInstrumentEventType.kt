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

/**
 * todo: description.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
enum class LiveInstrumentEventType {
    BREAKPOINT_ADDED,
    BREAKPOINT_APPLIED,
    BREAKPOINT_HIT,
    BREAKPOINT_REMOVED,

    LOG_ADDED,
    LOG_APPLIED,
    LOG_HIT,
    LOG_REMOVED,

    METER_ADDED,
    METER_APPLIED,
    METER_HIT,
    METER_UPDATED,
    METER_REMOVED,

    SPAN_ADDED,
    SPAN_APPLIED,
    SPAN_REMOVED
}
