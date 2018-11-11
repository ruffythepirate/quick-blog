package articles

import com.google.inject.Inject
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

class ArticlesApiController @Inject()
(cc: ControllerComponents,
  articlesRepository: ArticlesRepository)
(implicit ec: ExecutionContext) extends AbstractController(cc)
{

  implicit val articleFormat = ArticleConverter.format

  def createArticle() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson match{
      case Some(json) => {
        json.validate[Article] match {
          case JsSuccess(article, _) =>
            articlesRepository.insertArticle(article)
              .map(_ => Ok)
          case JsError(_) =>
            Future.successful(BadRequest)
        }
      }
      case None =>
        Future.successful(BadRequest)
    }
  }

  def updateArticle(id: Int) = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson match{
      case Some(json) => {
        json.validate[Article] match {
          case JsSuccess(article, _) =>
            articlesRepository.updateArticle(id, article)
              .map(_ => Ok)
          case JsError(_) =>
            Future.successful(BadRequest)
        }
      }
      case None =>
        Future.successful(BadRequest)
    }
  }

}
