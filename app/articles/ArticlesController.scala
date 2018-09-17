package articles

import com.google.inject.Inject
import markdown.MarkdownService
import play.api.mvc.{AbstractController, AnyContent, ControllerComponents, Request}

import scala.concurrent.ExecutionContext

class ArticlesController @Inject()
(cc: ControllerComponents,
 articlesRepository: ArticlesRepository,
 markdownService: MarkdownService)
(implicit ec: ExecutionContext) extends AbstractController(cc) {

  def showArticle(articleId: Int) = Action.async { implicit request: Request[AnyContent] =>
    articlesRepository.selectArticle(articleId)
      .map( article => article.copy(text = markdownService.renderText(article.text)))
      .map(article => Ok(views.html.articles.article(article.title, article.text)))
  }

  def showAllArticles() = Action.async {implicit request: Request[AnyContent] =>
    val articles = articlesRepository.selectAll()
      .map( articles => articles.map(article => article.copy(text = markdownService.renderText(article.text))))

    articles.map(articles => Ok(views.html.articles.articleList(articles)))
  }
}
