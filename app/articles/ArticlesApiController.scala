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


  def createArticle() = Action.async { implicit request: Request[AnyContent] =>

    implicit val articleFormat = ArticleConverter.format

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

}
