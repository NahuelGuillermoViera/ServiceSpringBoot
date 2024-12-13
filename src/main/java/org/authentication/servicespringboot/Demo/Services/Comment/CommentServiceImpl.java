package org.authentication.servicespringboot.Demo.Services.Comment;

import org.authentication.servicespringboot.Demo.DTO.CommentDTO;
import org.authentication.servicespringboot.Demo.Entities.Comment;
import org.authentication.servicespringboot.Demo.Entities.Publication;
import org.authentication.servicespringboot.Demo.Exceptions.BlogAppException;
import org.authentication.servicespringboot.Demo.Exceptions.ResourceNotFoundException;
import org.authentication.servicespringboot.Demo.Repositories.ICommentRepository;
import org.authentication.servicespringboot.Demo.Repositories.IPublicationRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

    private final ICommentRepository commentRepository;
    private final IPublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(ICommentRepository commentRepository, IPublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(Long id, CommentDTO commentDTO) {
        Comment comment = mapCommentDTOToComment(commentDTO);
        Publication publication = publicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publication", "id", id)
        );

        comment.setPublication(publication);

        Comment savedComment = commentRepository.save(comment);

        return mapCommentToCommentDTO(savedComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPublicationId(Long publicationId) {
        List<Comment> comments = commentRepository.findByPublicationId(publicationId);
        return comments.stream().map(this::mapCommentToCommentDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long publicationId, Long commentId) {
        return mapCommentToCommentDTO(findCommentInPublication(publicationId, commentId));
    }

    @Override
    public CommentDTO updateComment(Long publicationId, Long commentId, CommentDTO commentDTO) {
        Comment comment = findCommentInPublication(publicationId, commentId);

        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        Comment savedComment = commentRepository.save(comment);

        return mapCommentToCommentDTO(savedComment);
    }

    @Override
    public void deleteComment(Long publicationId, Long commentId) {
        commentRepository.delete(findCommentInPublication(publicationId, commentId));
    }

    //Private methods

    private CommentDTO mapCommentToCommentDTO(Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment mapCommentDTOToComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setName(commentDTO.getName());
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());

        return comment;
    }

    private Comment findCommentInPublication(Long publicationId ,Long commentId) {
        Publication publication = publicationRepository.findById(publicationId).orElseThrow(() ->
                new ResourceNotFoundException("Publication", "id", publicationId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId)
        );

        if (!comment.getPublication().getId().equals(publication.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
        }

        return comment;
    }
}
