package likelion13th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import likelion13th.blog.dto.*;
import likelion13th.blog.repository.ArticleRepository;
import likelion13th.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    public ArticleResponse addArticle(AddArticleRequest request) {

        Article article = request.toEntity();

        articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    public List<SimpleArticleResponse> getAllArticles() {

        List<Article> articleList = articleRepository.findAll();
        List<SimpleArticleResponse> articleResponseList = articleList.stream()
                .map(article -> SimpleArticleResponse.of(article))
                .toList();

        return articleResponseList;
    }

    public ArticleDetailResponse getArticle(Long id) {

        Article article = articleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cannot found article with id: " + id));

        List<CommentResponse> comments = getCommentList(article);

        return ArticleDetailResponse.of(article, comments);
    }

    @Transactional
    public ArticleResponse updateArticle(Long id, UpdateArticleRequest request) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다. " + id));

        if (!article.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("해당 글에 대한 수정 권한이 없습니다.");
        }

        article.update(request.getTitle(), request.getContent());
        Article savedArticle = articleRepository.save(article);

        return ArticleResponse.of(article);
    }

    @Transactional
    public void deleteArticle(Long id, DeleteRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 게시글을 찾을 수 없습니다. " + id));

        if (!request.getPassword().equals(article.getPassword())) {
            throw new RuntimeException("해당 글에 대한 삭제 권한이 없습니다.");
        }

        articleRepository.delete(article);
    }

    private List<CommentResponse> getCommentList(Article article) {

        return commentRepository.findByArticle(article).stream()
                .map(comment -> CommentResponse.of(comment))
                .toList();
    }

}
