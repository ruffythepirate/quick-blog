package health

class HealthService {

 def getStatus: HealthStatus = ???

}

case class HealthStatus(databaseUp: Boolean, responseTime: Option[Long])