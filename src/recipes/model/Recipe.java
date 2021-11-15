package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotBlank
    @Column(name = "category")
    private String category;

    @Column(name = "date")
    private LocalDateTime date;

    @NotBlank
    @Column(name = "description")
    private String description;

    @NotNull
    @Size(min = 1)
    @Column(name = "ingredients")
    private String[] ingredients;

    @NotNull
    @Size(min = 1)
    @Column(name = "directions")
    private String[] directions;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    @Setter
    private User user;
}
