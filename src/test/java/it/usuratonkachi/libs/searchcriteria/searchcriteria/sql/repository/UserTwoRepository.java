package it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.UserTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserTwoRepository extends JpaRepository<UserTwo, Long>, JpaSpecificationExecutor<UserTwo> {
}
