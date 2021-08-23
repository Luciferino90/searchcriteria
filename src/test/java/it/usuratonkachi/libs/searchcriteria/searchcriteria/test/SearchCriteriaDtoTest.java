package it.usuratonkachi.libs.searchcriteria.searchcriteria.test;

import it.usuratonkachi.libs.searchcriteria.mongo.GenericMongoQueryBuilder;
import it.usuratonkachi.libs.searchcriteria.mysql.QueryBuilder;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain.UserOneDomain;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.repository.UserOneDomainRepository;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.UserOne;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository.UserOneRepository;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.repository.UserTwoRepository;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.utility.MockData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest
public class SearchCriteriaDtoTest {

    @Autowired
    private UserOneDomainRepository mongoRepository;
    @Autowired
    private UserOneRepository userOneRepository;
    @Autowired
    private UserTwoRepository userTwoRepository;
    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    @Test
    public void test() {
        insertSomeData();
        Query mongoQuery = new GenericMongoQueryBuilder(UserOneDomain.class).buildQuery(MockData.getMongoSearchWrapper());
        List<UserOneDomain> exampleMongoDomains = reactiveMongoTemplate.find(mongoQuery, UserOneDomain.class).toStream().collect(Collectors.toList());
        log.info("Logging MONGO");
        System.out.println("MONGO QUERY: " + mongoQuery);

        Assertions.assertEquals(1, exampleMongoDomains.size());

        Specification<UserOne> sqlQuery = new QueryBuilder<>(UserOne.class).specificationBuilder(MockData.getMySQLSearchWrapper());
        log.info("Logging SQL");
        List<UserOne> exampleSqlDomains = userOneRepository.findAll(sqlQuery);
        log.info("SQL QUERY: " + sqlQuery.toString());

        Assertions.assertEquals(1, exampleSqlDomains.size());
    }

    private void insertSomeData() {
        MockData.getMongoUsers()
                .forEach(d -> {
                    try {
                        mongoRepository.save(d).toFuture().get();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });

        MockData.getUserOne()
                .forEach(d -> {
                    try {
                        userOneRepository.save(d);
                        userOneRepository.findAll();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });

        UserOne userOne = userOneRepository.findById(1L).orElseThrow(() -> new RuntimeException("FAIL"));
        UserOne userTwo = userOneRepository.findById(2L).orElseThrow(() -> new RuntimeException("FAIL"));
        UserOne userThree = userOneRepository.findById(3L).orElseThrow(() -> new RuntimeException("FAIL"));

        MockData.getUserTwo(userOne, userTwo, userThree)
                .forEach(d -> {
                    try {
                        userTwoRepository.save(d);
                        userTwoRepository.findAll();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                });

    }





}
