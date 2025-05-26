package likelion13th.blog.controller;
import likelion13th.blog.domain.Article;
import likelion13th.blog.dto.*;
import likelion13th.blog.service.ArticleService;
import likelion13th.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CommentService commentService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createArticle(@RequestBody AddArticleRequest request) {

        ArticleResponse response = articleService.addArticle(request);

        return ResponseEntity.ok(new ApiResponse(true, 201, "success", response));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> readAllArticles() {

        List<SimpleArticleResponse> articles = articleService.getAllArticles();

        return ResponseEntity.ok(new ApiResponse(true, 200, "success", articles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> readArticle(@PathVariable Long id) {

        ArticleDetailResponse response = articleService.getArticle(id);

        return ResponseEntity.ok(new ApiResponse(true, 200, "success", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {

        ArticleResponse response = articleService.updateArticle(id, request);
        return ResponseEntity.ok(new ApiResponse(true, 204, "게시글 수정 성공", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteArticle(@PathVariable long id, @RequestBody DeleteRequest request) {

        articleService.deleteArticle(id, request);
        return ResponseEntity.ok(new ApiResponse(true, 204, "게시글 삭제 성공"));
    }

    @PostMapping("/{articleId}")
    public ResponseEntity<ApiResponse> createComment(@PathVariable Long articleId, @RequestBody AddCommentRequest request) {

        CommentResponse response = commentService.addComment(articleId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, 201, "댓글 등록 성공", response));
    }

}
