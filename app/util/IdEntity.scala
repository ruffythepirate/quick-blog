package util

import play.api.libs.json.{Format, Json}

case class IdEntity(id: Int)

object IdEntityFormat {
  implicit val format: Format[IdEntity] = Json.format[IdEntity]
}
