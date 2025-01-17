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
package spp.protocol.service

import io.vertx.codegen.annotations.GenIgnore
import io.vertx.codegen.annotations.ProxyGen
import io.vertx.codegen.annotations.VertxGen
import io.vertx.core.Future
import io.vertx.core.Vertx
import io.vertx.core.eventbus.DeliveryOptions
import io.vertx.core.eventbus.ReplyException
import io.vertx.core.impl.ContextInternal
import io.vertx.core.json.JsonObject
import spp.protocol.artifact.metrics.MetricStep
import spp.protocol.artifact.trace.TraceStack
import spp.protocol.platform.general.Order
import spp.protocol.platform.general.Scope
import spp.protocol.platform.general.SelectedRecord
import spp.protocol.service.SourceServices.LIVE_VIEW
import spp.protocol.view.HistoricalView
import spp.protocol.view.LiveView
import spp.protocol.view.rule.ViewRule
import java.time.Instant

/**
 * Back-end service for managing [LiveView]s.
 *
 * @since 0.3.0
 * @author [Brandon Fergerson](mailto:bfergerson@apache.org)
 */
@ProxyGen
@VertxGen
@Suppress("TooManyFunctions") // public API
interface LiveViewService {

    @GenIgnore
    companion object {
        @JvmStatic
        fun createProxy(vertx: Vertx, accessToken: String? = null): LiveViewService {
            val deliveryOptions = DeliveryOptions().apply {
                accessToken?.let { addHeader("auth-token", it) }
                (Vertx.currentContext() as? ContextInternal)?.localContextData()?.forEach {
                    addHeader(it.key.toString(), it.value.toString())
                }
            }
            return LiveViewServiceVertxEBProxy(vertx, LIVE_VIEW, deliveryOptions)
        }
    }

    fun getRules(): Future<List<ViewRule>>
    fun getRule(ruleName: String): Future<ViewRule?>
    fun saveRule(rule: ViewRule): Future<ViewRule>
    fun deleteRule(ruleName: String): Future<ViewRule?>

    @GenIgnore
    fun saveRuleIfAbsent(rule: ViewRule): Future<ViewRule> {
        return saveRule(rule).recover { error ->
            if (error is ReplyException && error.failureCode() == 409) {
                Future.succeededFuture(rule)
            } else {
                Future.failedFuture(error)
            }
        }
    }

    fun addLiveView(subscription: LiveView): Future<LiveView>
    fun updateLiveView(id: String, subscription: LiveView): Future<LiveView>
    fun removeLiveView(id: String): Future<LiveView>
    fun getLiveView(id: String): Future<LiveView>
    fun getLiveViews(): Future<List<LiveView>>
    fun clearLiveViews(): Future<List<LiveView>>
    fun getLiveViewStats(): Future<JsonObject>

    fun sortMetrics(
        name: String,
        parentService: String?,
        normal: Boolean?,
        scope: Scope?,
        topN: Int,
        order: Order,
        step: MetricStep,
        start: Instant,
        stop: Instant?
    ): Future<List<SelectedRecord>>

    @GenIgnore
    fun getHistoricalMetrics(
        entityIds: List<String>,
        metricIds: List<String>,
        step: MetricStep,
        start: Instant
    ): Future<HistoricalView> {
        return getHistoricalMetrics(entityIds, metricIds, step, start, null)
    }

    @GenIgnore
    fun getHistoricalMetrics(
        entityIds: List<String>,
        metricIds: List<String>,
        step: MetricStep,
        start: Instant,
        stop: Instant?
    ): Future<HistoricalView> {
        return getHistoricalMetrics(entityIds, metricIds, step, start, stop, emptyList())
    }

    fun getHistoricalMetrics(
        entityIds: List<String>,
        metricIds: List<String>,
        step: MetricStep,
        start: Instant,
        stop: Instant?,
        labels: List<String>
    ): Future<HistoricalView>

    fun getTraceStack(traceId: String): Future<TraceStack?>
}
