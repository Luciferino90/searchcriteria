package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.ExampleSqlDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExampleSqlRepository extends JpaRepository<ExampleSqlDomain, Long>, JpaSpecificationExecutor<ExampleSqlDomain> {
}
