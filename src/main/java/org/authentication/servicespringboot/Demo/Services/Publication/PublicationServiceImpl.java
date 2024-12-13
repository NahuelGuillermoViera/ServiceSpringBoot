package org.authentication.servicespringboot.Demo.Services.Publication;

import org.authentication.servicespringboot.Demo.DTO.PublicationDTO;
import org.authentication.servicespringboot.Demo.DTO.PublicationResponse;
import org.authentication.servicespringboot.Demo.Entities.Publication;
import org.authentication.servicespringboot.Demo.Exceptions.ResourceNotFoundException;
import org.authentication.servicespringboot.Demo.Repositories.IPublicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationServiceImpl implements IPublicationService {
    private final IPublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationServiceImpl(IPublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        //Convierto DTO a entidad
        Publication publication = mapPublication(publicationDTO);

        Publication newPublication = publicationRepository.save(publication);

        //Convertimos entidad a DTO

        return mapDTO(newPublication);
    }

    @Override
    public PublicationResponse getAllPublications(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Publication> publications = publicationRepository.findAll(pageable);

        List<Publication> publicationsList = publications.getContent();

        List<PublicationDTO> content = publicationsList.stream().map(this::mapDTO).toList();

        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setContent(content);
        publicationResponse.setPageNumber(pageNumber);
        publicationResponse.setPageSize(pageSize);
        publicationResponse.setTotalElements(publications.getTotalElements());
        publicationResponse.setLast(publications.isLast());

        return publicationResponse;
    }


    @Override
    public PublicationDTO getPublicationById(Long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publication", "id", id)
        );
        return mapDTO(publication);
    }

    @Override
    public PublicationDTO updatePublicationById(Long id, PublicationDTO publicationDTO) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() ->
                    new ResourceNotFoundException("Publication", "id", id)
        );

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());

        Publication updatedPublication = publicationRepository.save(publication);

        return mapDTO(updatedPublication);
    }

    @Override
    public void deletePublicationById(Long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Publication", "id", id)
        );

        publicationRepository.delete(publication);
    }


    //Metodos Privados
    private PublicationDTO mapDTO(Publication publication) {
        return modelMapper.map(publication, PublicationDTO.class);
    }

    private Publication mapPublication(PublicationDTO publicationDTO) {
        return modelMapper.map(publicationDTO, Publication.class);
    }


}
