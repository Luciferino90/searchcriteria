package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "wife")
public class UserTwo implements Serializable {

    @Id
    private Long id;
    private String name;
    private String surname;

    private Date date;
    private LocalDateTime localDateTime;
    private ZonedDateTime zonedDateTime;

    private Long diskSizeUsed;
    private Integer diskSizeTotal;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "surnames", referencedColumnName = "surname")
    private List<UserOne> userone;

}
