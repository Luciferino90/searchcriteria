package it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.repository;

import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain.ExampleMongoDomain;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ExampleMongoRepository extends ReactiveMongoRepository<ExampleMongoDomain, String> {
}
