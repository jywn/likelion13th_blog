package likelion13th.blog.controller;
import likelion13th.blog.domain.Article;
import likelion13th.blog.dto.*;
import likelion13th.blog.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

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

        ArticleResponse response = articleService.getArticle(id);

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
}
