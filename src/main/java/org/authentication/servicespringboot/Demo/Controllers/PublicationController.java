package org.authentication.servicespringboot.Demo.Controllers;

import jakarta.validation.Valid;
import org.authentication.servicespringboot.Demo.DTO.PublicationDTO;
import org.authentication.servicespringboot.Demo.DTO.PublicationResponse;
import org.authentication.servicespringboot.Demo.Services.Publication.IPublicationService;
import org.authentication.servicespringboot.Utilities.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/demo/publication")
public class PublicationController {

    private final IPublicationService publicationService;

    @Autowired
    public PublicationController(IPublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping
    public ResponseEntity<PublicationResponse> getAllPublications(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFECT_PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFECT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFECT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFECT_SORT_DIRECTION, required = false) String sortDir)
    {
        return new ResponseEntity<>(
                publicationService.getAllPublications(pageNumber, pageSize, sortBy, sortDir),
                HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PublicationDTO> getPublicationsById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(
                publicationService.getPublicationById(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PublicationDTO> addPublication(@Valid @RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<>(
                publicationService.createPublication(publicationDTO), HttpStatus.CREATED
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@Valid @PathVariable(name = "id") Long id, @RequestBody PublicationDTO publicationDTO) {
        return ResponseEntity.ok(
                publicationService.updatePublicationById(id, publicationDTO)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePublication(@PathVariable(name = "id") Long id) {
        publicationService.deletePublicationById(id);

        return ResponseEntity.ok("Publication deleted");
    }
}
