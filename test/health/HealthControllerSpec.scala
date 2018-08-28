package health

import org.mockito.Mockito._
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll}
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._
import util.DockerPostgresService

class HealthControllerSpec extends PlaySpec with BeforeAndAfterAll with BeforeAndAfter with MockitoSugar with DockerPostgresService {

  var healthService: HealthService = _

  before {
    postgresContainer.isReady().value
    healthService = mock[HealthService]

  }


  "HealthController GET" should {
    "render the index page from a new instance of controller" in {
      val controller = new HealthController(stubControllerComponents(), healthService)
      val healthStatus = controller.index().apply(FakeRequest(GET, "/internal/health"))

      verify(healthService).getStatus
      status(healthStatus) mustBe OK
      contentType(healthStatus) mustBe Some("text/html")
    }
  }
}
