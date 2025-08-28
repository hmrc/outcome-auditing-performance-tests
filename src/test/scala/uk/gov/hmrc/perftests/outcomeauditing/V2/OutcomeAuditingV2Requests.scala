/*
 * Copyright 2023 HM Revenue & Customs
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

package uk.gov.hmrc.perftests.outcomeauditing.V2

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object OutcomeAuditingV2Requests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("outcome-auditing")
  val route: String   = "/outcome-auditing/v2/outcomes/report"

  val outcomeJson: String =
    """{
      |  "correlationData": {
      |    "correlationId": "33df37a4-a535-41fe-8032-7ab718b45526",
      |    "correlationIdType": "ACKNOWLEDGEMENT_ID"
      |  },
      |  "submitter": "sa-reg",
      |  "decisionData": [
      |    {
      |      "businessEvent": "SARegistrationSubmitted",
      |      "decision": "ACCEPTED",
      |      "reasons": [
      |        "LOW_RISK_SCORE"
      |      ],
      |      "evidence": [
      |        {
      |          "decisionMethod": "AUTOMATIC",
      |          "decisionSystem": "sa-reg",
      |          "decisionTimestamp": "2025-07-09T08:14:13Z",
      |          "decisionAuthority": "sa-reg-authority",
      |          "attributes": [
      |            {
      |              "attributeType": "UTR",
      |              "attributeValue": "123456789"
      |            },
      |            {
      |              "attributeType": "NINO",
      |              "attributeValue": "987654321"
      |            }
      |          ]
      |        }
      |      ]
      |    }
      |  ]
      |}""".stripMargin

  val reportAnOutcome: HttpRequestBuilder =
    http("Report a payment allocation outcome")
      .post(s"$baseUrl$route")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "test-only")
      .body(StringBody(outcomeJson))
      .asJson
      .check(status.is(201))
      .check(bodyString.is("""{"code":"OUTCOME_REPORTED","message":"success"}"""))
}
