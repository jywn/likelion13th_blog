package likelion13th.blog.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import likelion13th.blog.domain.Article;
import likelion13th.blog.domain.Comment;
import likelion13th.blog.dto.AddCommentRequest;
import likelion13th.blog.dto.CommentResponse;
import likelion13th.blog.dto.DeleteRequest;
import likelion13th.blog.repository.ArticleRepository;
import likelion13th.blog.repository.CommentRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    @Transactional
    public CommentResponse addComment(long articleId, AddCommentRequest request) {

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("해당 ID의 게시글을 찾을 수 없습니다."));

        Comment comment = request.toEntity(article);
        Comment save = commentRepository.save(comment);

        article.increaseCommentCount();

        return CommentResponse.of(save);
    }

    @Transactional
    public void deleteComment(long commentId, DeleteRequest request) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글을 찾을 수 없습니다."));

        if (!request.getPassword().equals(comment.getPassword())) {
            throw new RuntimeException("해당 댓글에 대한 삭제 권한이 없습니다.");
        }

        commentRepository.deleteById(commentId);

        comment.getArticle().decreaseCommentCount();
    }
}
