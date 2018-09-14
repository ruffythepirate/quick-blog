package articles

import org.joda.time.DateTime


case class ArticleViewModel(id: Int,
                            title: String,
                            text: String,
                            author: String,
                            created: DateTime)
