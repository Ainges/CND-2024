package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Comment {
    @Id
    private Long id;

    private String text;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
