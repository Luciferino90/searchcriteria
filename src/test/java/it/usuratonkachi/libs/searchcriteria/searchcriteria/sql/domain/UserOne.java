package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.config.EnumTestAttributeConverter;
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
@Entity(name = "userone")
public class UserOne implements Serializable {

    @Id
    private Long id;
    private String name;
    @Column(name = "surname")
    private String surname;
    private Boolean active;

    @JsonIgnore
    @ManyToMany(mappedBy = "userOnes", fetch = FetchType.EAGER)
    private List<UserTwo> userTwos;

    @Column(name = "enum_test", columnDefinition = "TINYINT")
    @Convert(converter = EnumTestAttributeConverter.class)
    private EnumTest enumTest;

}
