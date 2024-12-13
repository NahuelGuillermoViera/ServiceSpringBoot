package org.authentication.servicespringboot.Demo.Services.Publication;


import org.authentication.servicespringboot.Demo.DTO.PublicationDTO;
import org.authentication.servicespringboot.Demo.DTO.PublicationResponse;

public interface IPublicationService {
    PublicationDTO createPublication(PublicationDTO publicationDTO);

    PublicationResponse getAllPublications(int pageNumber, int pageSize, String sortBy, String sortDir);

    PublicationDTO getPublicationById(Long id);

    PublicationDTO updatePublicationById(Long id, PublicationDTO publicationDTO);

    void deletePublicationById(Long id);
}
