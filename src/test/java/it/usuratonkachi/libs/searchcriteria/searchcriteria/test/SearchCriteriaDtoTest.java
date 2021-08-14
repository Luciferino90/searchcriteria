package it.usuratonkachi.libs.searchcriteria.searchcriteria.test;

import it.usuratonkachi.libs.searchcriteria.criteria.SearchOperator;
import it.usuratonkachi.libs.searchcriteria.mongo.GenericMongoQueryBuilder;
import it.usuratonkachi.libs.searchcriteria.mysql.QueryBuilder;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain.ExampleMongoDomain;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.repository.ExampleMongoRepository;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.Husband;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.Wife;
import it.usuratonkachi.libs.searchcriteria.criteria.ComposedSearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.SearchCriteria;
import it.usuratonkachi.libs.searchcriteria.criteria.LogicalOperator;
import it.usuratonkachi.libs.searchcriteria.criteria.SingleSearchCriteria;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository.HusbandRepository;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository.WifeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class SearchCriteriaDtoTest {

    @Autowired
    private ExampleMongoRepository mongoRepository;
    @Autowired
    private HusbandRepository husbandRepository;
    @Autowired
    private WifeRepository wifeRepository;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void test() {
        insertSomeData();
        Query mongoQuery = new GenericMongoQueryBuilder(ExampleMongoDomain.class).buildQuery(getMongoSearchWrapper());
        List<ExampleMongoDomain> exampleMongoDomains = reactiveMongoTemplate.find(mongoQuery, ExampleMongoDomain.class).toStream().collect(Collectors.toList());
        log.info("Logging MONGO");
        System.out.println("MONGO QUERY: " + mongoQuery);

        Assertions.assertTrue(exampleMongoDomains.size() == 2);

        Specification<Husband> sqlQuery = new QueryBuilder<>(Husband.class).specificationBuilder(getMySQLSearchWrapper());
        log.info("Logging SQL");
        List<Husband> exampleSqlDomains = husbandRepository.findAll(sqlQuery);
        log.info("SQL QUERY: " + sqlQuery.toString());

        Assertions.assertTrue(exampleSqlDomains.size() == 1);
    }

    private void insertSomeData() {
        List.of(
                ExampleMongoDomain.builder()
                        .name("ruggero")
                        .surname("giarnasso")
                        .build(),
                ExampleMongoDomain.builder()
                        .name("giuseppe")
                        .build(),
                ExampleMongoDomain.builder()
                        .name("Sora")
                        .surname("giarnasso")
                        .build()
        ).forEach(d -> {
            try {
                mongoRepository.save(d).toFuture().get();
                mongoRepository.findAll().toStream().collect(Collectors.toList());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

        List.of(
                Husband.builder()
                        .name("ruggero")
                        .surname("giarnasso")
                        .id(1L)
                        .build(),
                Husband.builder()
                        .name("giuseppe")
                        .surname("dinazaret")
                        .id(2L)
                        .build(),
                Husband.builder()
                        .name("Soro")
                        .surname("Kai")
                        .id(3L)
                        .build()
        ).forEach(d -> {
            try {
                husbandRepository.save(d);
                husbandRepository.findAll();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

        List.of(
                Wife.builder()
                        .name("ruggera")
                        .surname("giarnasso")
                        .id(4L)
                        .husbands(husbandRepository.findById(1L).map(List::of).orElse(new ArrayList<>()))
                        .build(),
                Wife.builder()
                        .name("maria")
                        .surname("dinazaret")
                        .id(5L)
                        .husbands(husbandRepository.findById(2L).map(List::of).orElse(new ArrayList<>()))
                        .build(),
                Wife.builder()
                        .name("Sora")
                        .surname("Kai")
                        .id(6L)
                        .husbands(husbandRepository.findById(3L).map(List::of).orElse(new ArrayList<>()))
                        .build()
        ).forEach(d -> {
            try {
                wifeRepository.save(d);
                wifeRepository.findAll();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });

    }

    private SearchCriteria getMongoSearchWrapper() {
        return ComposedSearchCriteria.builder()
                .logicalOperator(LogicalOperator.OR)
                .innerSearchCriteria(
                        List.of(
                                ComposedSearchCriteria.builder()
                                        .logicalOperator(LogicalOperator.AND)
                                        .innerSearchCriteria(
                                                List.of(
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("ruggero")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("surname")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("giarnasso")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                ComposedSearchCriteria.builder()
                                        .logicalOperator(LogicalOperator.OR)
                                        .innerSearchCriteria(
                                                List.of(
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("ettore")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("giovanni")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                SingleSearchCriteria.builder()
                                        .field("name")
                                        .operator(SearchOperator.EQUAL)
                                        .value("giuseppe")
                                        .build()
                        )
                )
                .build();
    }

    private SearchCriteria getMySQLSearchWrapper() {
        return ComposedSearchCriteria.builder()
                .logicalOperator(LogicalOperator.OR)
                .innerSearchCriteria(
                        List.of(
                                ComposedSearchCriteria.builder()
                                        .logicalOperator(LogicalOperator.AND)
                                        .innerSearchCriteria(
                                                List.of(
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("giuseppe")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("wives.name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("maria")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                ComposedSearchCriteria.builder()
                                        .logicalOperator(LogicalOperator.AND)
                                        .innerSearchCriteria(
                                                List.of(
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("ruggero")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("surname")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("giarnasso")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                ComposedSearchCriteria.builder()
                                        .logicalOperator(LogicalOperator.OR)
                                        .innerSearchCriteria(
                                                List.of(
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("ettore")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("giovanni")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                SingleSearchCriteria.builder()
                                        .field("name")
                                        .operator(SearchOperator.EQUAL)
                                        .value("giuseppe")
                                        .build()
                        )
                )
                .build();
    }

}
