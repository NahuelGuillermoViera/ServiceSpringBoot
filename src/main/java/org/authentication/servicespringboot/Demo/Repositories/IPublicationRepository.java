package org.authentication.servicespringboot.Demo.Repositories;

import org.authentication.servicespringboot.Demo.Entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPublicationRepository extends JpaRepository<Publication, Long> {
    Long id(Long id);
}
