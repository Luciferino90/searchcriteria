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
@Entity(name = "husband")
public class Husband implements Serializable {

    @Id
    private Long id;
    private String name;
    @Column(name = "surname")
    private String surname;

    @JsonIgnore
    @ManyToMany(mappedBy = "husbands", fetch = FetchType.EAGER)
    private List<Wife> wives;

}
