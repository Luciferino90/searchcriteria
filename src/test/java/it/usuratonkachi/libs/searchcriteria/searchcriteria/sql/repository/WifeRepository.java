package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.Wife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WifeRepository extends JpaRepository<Wife, Long>, JpaSpecificationExecutor<Wife> {
}
