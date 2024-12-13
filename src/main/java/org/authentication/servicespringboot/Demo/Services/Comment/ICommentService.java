package org.authentication.servicespringboot.Demo.Services.Comment;

import org.authentication.servicespringboot.Demo.DTO.CommentDTO;

import java.util.List;

public interface ICommentService {
    CommentDTO createComment(Long id, CommentDTO commentDTO);
    List<CommentDTO> getCommentsByPublicationId(Long postId);
    CommentDTO getCommentById(Long publicationId, Long commentId);
    CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO commentDTO);
    void deleteComment(Long publicationId, Long commentId);

}
