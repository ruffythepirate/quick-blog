package articles

import com.google.inject.Inject
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}
import util.{IdEntity, IdEntityFormat}

import scala.concurrent.{ExecutionContext, Future}

class ArticlesApiController @Inject()
(cc: ControllerComponents,
  articlesRepository: ArticlesRepository)
(implicit ec: ExecutionContext) extends AbstractController(cc)
{

  implicit val articleFormat = ArticleFormat.format
  implicit val idEntityFormat = IdEntityFormat.format

  def createArticle() = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson match{
      case Some(json) => {
        json.validate[Article] match {
          case JsSuccess(article, _) =>
            articlesRepository.insertArticle(article)
              .map(article => Ok(Json.toJson(IdEntity(article.id.get))))
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
              .map(article => Ok(Json.toJson(article)))
          case JsError(_) =>
            Future.successful(BadRequest)
        }
      }
      case None =>
        Future.successful(BadRequest)
    }
  }

}
