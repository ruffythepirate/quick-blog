package articles

import com.google.inject.Inject
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext

class ArticlesController @Inject()(cc: ControllerComponents, articlesRepository: ArticlesRepository)(implicit ec: ExecutionContext) extends AbstractController(cc) {


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def showArticle(articleId: Int) = Action.async { implicit request: Request[AnyContent] =>
    articlesRepository.selectArticle(articleId).map(
      article =>
        Ok(views.html.articles.article(article.title, article.text))
    )
  }
}
