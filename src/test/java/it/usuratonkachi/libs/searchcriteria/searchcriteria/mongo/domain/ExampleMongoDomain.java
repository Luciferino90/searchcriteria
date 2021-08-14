package it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("example")
public class ExampleMongoDomain implements Serializable {

    @Id
    private String id;
    private String name;
    private String surname;

}
