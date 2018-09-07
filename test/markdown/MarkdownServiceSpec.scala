package markdown

import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec

class MarkdownServiceSpec extends PlaySpec with BeforeAndAfter {

  var cut: MarkdownService = _

  before{
    cut = new MarkdownService()
  }

  "MarkdownService.renderText" should {

    "render a title" in {
      val result = cut.renderText("# title")
      assert(result.trim === "<h1>title</h1>")
    }

    "render a h2 title" in {
      val result = cut.renderText("## title")
      assert(result.trim === "<h2>title</h2>")
    }
  }

}
