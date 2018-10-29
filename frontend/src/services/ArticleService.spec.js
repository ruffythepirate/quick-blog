import articleService from './ArticleService';

jest.mock('axios');

const axiosMock = require('axios');

describe('ArticleService', () => {
  beforeEach(() => {
    axiosMock.mockReset();
  });

  it('should update article if article has id', () => {
    const article = {
      id: 'my-id',
    };
    articleService.saveOrUpdateArticle(article);

    expect(axiosMock.put).toHaveBeenCalledWith('/api/articles/my-id', article);
  });

  it('should create article if article has no id', () => {
    const article = {
    };
    articleService.saveOrUpdateArticle(article);

    expect(axiosMock.post).toHaveBeenCalledWith('/api/articles', article);
  });
});
