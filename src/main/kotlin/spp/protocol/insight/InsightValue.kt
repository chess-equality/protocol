/*
 * Source++, the continuous feedback platform for developers.
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
package spp.protocol.insight

data class InsightValue<V>(
    val type: InsightType,
    val value: V,
    val confidence: Double = 1.0,
    val derived: Boolean = false
) {
    fun asDerived() = InsightValue(type, value, confidence, true)
    fun withConfidence(confidence: Double) = InsightValue(type, value, confidence, derived)

    companion object {
        fun <V> of(type: InsightType, value: V) = InsightValue(type, value)
    }
}