package org.authentication.servicespringboot.Demo.Controllers;

import jakarta.validation.Valid;
import org.authentication.servicespringboot.Demo.DTO.CommentDTO;
import org.authentication.servicespringboot.Demo.Services.Comment.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/demo/publication")
public class CommentController {
    private final ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("{publicationId}/comments")
    public ResponseEntity<List<CommentDTO>> getAllCommentsByPublicationId(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(commentService.getCommentsByPublicationId(publicationId), HttpStatus.OK);
    }

    @GetMapping("{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "publicationId") Long publicationId, @PathVariable(value = "commentId") Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(publicationId, commentId), HttpStatus.OK);
    }

    @PostMapping("{publicationId}/comment")
    public ResponseEntity<CommentDTO> saveComment(@PathVariable(value = "publicationId") long publicationId, @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(publicationId, commentDTO), HttpStatus.CREATED);
    }

    @PutMapping("{publicationId}/comment/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable(value = "publicationId") Long publicationId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody CommentDTO commentDTO)
    {
        return new ResponseEntity<>(commentService.updateComment(publicationId, commentId, commentDTO), HttpStatus.OK);
    }

    @DeleteMapping("{publicationId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(value = "publicationId") Long publicationId, @PathVariable(value = "commentId") Long commentId) {
        commentService.deleteComment(publicationId, commentId);
        return new ResponseEntity<>("Commentario eliminado correctamente", HttpStatus.OK);
    }

}
