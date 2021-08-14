package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wife")
public class Wife implements Serializable {

    @Id
    private Long id;
    private String name;
    private String surname;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "surnames", referencedColumnName = "surname")
    private List<Husband> husbands;

}
