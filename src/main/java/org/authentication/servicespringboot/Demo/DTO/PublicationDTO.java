package org.authentication.servicespringboot.Demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.authentication.servicespringboot.Demo.Entities.Comment;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class PublicationDTO {
    private Long id;

    @NotEmpty
    @Size(min = 2, max = 50, message = "El titulo de la publicacion debe estar entre 2 y 50 caracteres")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "La descripcion debe tener como minimo 10 caracteres")
    private String description;

    @NotEmpty
    private String content;

    private Set<Comment> comments = new HashSet<>();

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
