package it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("userone")
public class UserOneDomain implements Serializable {

    @Id
    private String id;
    private String name;
    private String surname;
    private Boolean active;

    private Date date;
    private LocalDateTime localdatetime;

    private Long disksizeused;
    private Integer disksizetotal;

    private EnumTest enumTest;

}
