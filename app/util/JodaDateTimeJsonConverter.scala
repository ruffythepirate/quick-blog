package util

import org.joda.time.DateTime
import play.api.libs.json.JodaWrites.JodaDateTimeNumberWrites
import play.api.libs.json.{JodaReads, Reads, Writes}

trait JodaDateTimeJsonConverter {
  implicit val jodaWriter: Writes[DateTime] = JodaDateTimeNumberWrites
  implicit val jodaReader: Reads[DateTime] = JodaReads.DefaultJodaDateTimeReads
}
