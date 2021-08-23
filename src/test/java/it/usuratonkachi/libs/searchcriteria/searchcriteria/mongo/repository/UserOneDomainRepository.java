package it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain.UserOneDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserOneDomainRepository extends ReactiveMongoRepository<UserOneDomain, String> {
}
