package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "example")
public class ExampleSqlDomain implements Serializable {

    @Id
    private Long id;
    private String name;
    private String surname;

}
