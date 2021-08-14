package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.Husband;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface HusbandRepository extends JpaRepository<Husband, Long>, JpaSpecificationExecutor<Husband> {
}
