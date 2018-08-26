package health

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

class HealthControllerSpec extends PlaySpec with GuiceOneAppPerTest with Injecting with BeforeAndAfter with MockitoSugar {

  var healthService: HealthService = _

  before {
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
