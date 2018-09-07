package markdown

import org.commonmark.parser.Parser
import org.commonmark.renderer.html.HtmlRenderer

class MarkdownService {

  val parser = Parser.builder().build()
  val renderer = HtmlRenderer.builder().build()


  def renderText(markdownContent: String): String = {
    val document = parser.parse(markdownContent)
    renderer.render(document)
  }
}
