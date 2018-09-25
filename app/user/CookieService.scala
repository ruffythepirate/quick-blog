package user

import play.api.mvc.Cookie
import play.api.mvc.Cookie.SameSite

class CookieService {

  def createGoldenCookie(name: String, value: String): Cookie = {
    Cookie(name,
      value,
      maxAge = Some(60 * 60 * 1),
      path = "/",
      domain = None,
      secure = true,
      httpOnly = true,
      sameSite = Some(SameSite.Strict))
  }

}
