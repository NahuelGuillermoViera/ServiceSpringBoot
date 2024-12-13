package org.authentication.servicespringboot.Demo.Repositories;

import org.authentication.servicespringboot.Demo.Entities.Comment;
import org.authentication.servicespringboot.Demo.Entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByPublicationId(Long publicationId);
}
