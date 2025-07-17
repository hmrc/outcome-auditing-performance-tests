import sbt._

object Dependencies {

  private val gatlingVersion = "3.6.1"

  val test = Seq(
    "uk.gov.hmrc"          %% "performance-test-runner"   % "6.1.0"         % Test
  )
}
