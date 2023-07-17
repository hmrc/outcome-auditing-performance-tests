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

package uk.gov.hmrc.perftests.outcomeauditing

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object OutcomeAuditingRequests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("outcome-auditing")
  val route: String   = "/report/outcome"

  val paymentAllocationJson =
    """{
      |  "correlationId": "33df37a4-a535-41fe-8032-7ab718b45526",
      |  "submitter": "ipp",
      |  "submission": {
      |    "submissionType": "bank-account",
      |    "submissionAttribute": {
      |      "sortCode": "608580",
      |      "accountNumber": "48835625"
      |    }
      |  },
      |  "outcome": {
      |    "outcomeType": "payment-allocation",
      |    "decision": "PAYMENT_ALLOCATED",
      |    "reasons": "ACCOUNT_ALLOCATED_TO_DETAILS",
      |    "evidence": {
      |      "sa_utr": "0123456789",
      |      "paye_ref": "ABC/A1234",
      |      "full_name": "Jane Smith",
      |      "user_id": "0123456789112345"
      |    }
      |  }
      |}""".stripMargin

  val reportPaymentAllocationOutcome: HttpRequestBuilder =
    http("Report a payment allocation outcome")
      .post(s"$baseUrl$route")
      .header(HttpHeaderNames.ContentType, "application/json")
      .header(HttpHeaderNames.UserAgent, "test-user-agent")
      .body(StringBody(paymentAllocationJson))
      .asJson
      .check(status.is(200))
      .check(jsonPath("$.code").is("ok"))
}
