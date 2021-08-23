package it.usuratonkachi.libs.searchcriteria.searchcriteria.utility;

import it.usuratonkachi.libs.searchcriteria.common.TypeHandlerConverters;
import it.usuratonkachi.libs.searchcriteria.criteria.*;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.enumerator.EnumTest;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.mongo.domain.UserOneDomain;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.UserOne;
import it.usuratonkachi.libs.searchcriteria.searchcriteria.sql.domain.UserTwo;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@UtilityClass
public class MockData {

    public List<UserOneDomain> getMongoUsers() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTime2 = zonedDateTime1.plusDays(100);
        ZonedDateTime zonedDateTime3 = zonedDateTime1.minusDays(100);

        String dateString1 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString2 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString3 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Date date1 = TypeHandlerConverters.toDate(dateString1);
        Date date2 = TypeHandlerConverters.toDate(dateString2);
        Date date3 = TypeHandlerConverters.toDate(dateString3);

        LocalDateTime localDateTime1 = TypeHandlerConverters.toLocalDate(dateString1);
        LocalDateTime localDateTime2 = TypeHandlerConverters.toLocalDate(dateString2);
        LocalDateTime localDateTime3 = TypeHandlerConverters.toLocalDate(dateString3);

        Long diskSizeUsed1 = 1L;
        Long diskSizeUsed2 = 10L;
        Long diskSizeUsed3 = 20L;
        Integer diskSizeTotal1 = 1;
        Integer diskSizeTotal2 = 10;
        Integer diskSizeTotal3 = 20;

        return List.of(
                UserOneDomain.builder()
                        .name("ruggero")
                        .surname("giarnasso")
                        .date(date1)
                        .localdatetime(localDateTime1)
                        .disksizeused(diskSizeUsed1)
                        .disksizetotal(diskSizeTotal1)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build(),
                UserOneDomain.builder()
                        .name("giuseppe")
                        .date(date1)
                        .localdatetime(localDateTime2)
                        .disksizeused(diskSizeUsed2)
                        .disksizetotal(diskSizeTotal2)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build(),
                UserOneDomain.builder()
                        .name("Sora")
                        .surname("giarnasso")
                        .date(date1)
                        .localdatetime(localDateTime3)
                        .disksizeused(diskSizeUsed3)
                        .disksizetotal(diskSizeTotal3)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build()
        );
    }

    public List<UserOne> getUserOne() {
        return List.of(
                UserOne.builder()
                        .name("ruggero")
                        .surname("giarnasso")
                        .id(1L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build(),
                UserOne.builder()
                        .name("giuseppe")
                        .surname("dinazaret")
                        .id(2L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build(),
                UserOne.builder()
                        .name("Soro")
                        .surname("Kai")
                        .id(3L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .build()
        );
    }

    public List<UserTwo> getUserTwo(UserOne userOne, UserOne userTwo, UserOne userThree) {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTime2 = zonedDateTime1.plusDays(100);
        ZonedDateTime zonedDateTime3 = zonedDateTime1.minusDays(100);

        String dateString1 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString2 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString3 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        Date date1 = TypeHandlerConverters.toDate(dateString1);
        Date date2 = TypeHandlerConverters.toDate(dateString2);
        Date date3 = TypeHandlerConverters.toDate(dateString3);

        LocalDateTime localDateTime1 = TypeHandlerConverters.toLocalDate(dateString1);
        LocalDateTime localDateTime2 = TypeHandlerConverters.toLocalDate(dateString2);
        LocalDateTime localDateTime3 = TypeHandlerConverters.toLocalDate(dateString3);

        Long diskSizeUsed1 = 1L;
        Long diskSizeUsed2 = 10L;
        Long diskSizeUsed3 = 20L;
        Integer diskSizeTotal1 = 1;
        Integer diskSizeTotal2 = 10;
        Integer diskSizeTotal3 = 20;

        return List.of(
                UserTwo.builder()
                        .name("ruggera")
                        .surname("giarnasso")
                        .id(1L)
                        .userone(List.of(userOne))
                        .date(date1)
                        .localDateTime(localDateTime1)
                        .zonedDateTime(zonedDateTime1)
                        .diskSizeUsed(diskSizeUsed1)
                        .diskSizeTotal(diskSizeTotal1)
                        .build(),
                UserTwo.builder()
                        .name("maria")
                        .surname("dinazaret")
                        .id(2L)
                        .userone(List.of(userTwo))
                        .date(date2)
                        .localDateTime(localDateTime2)
                        .zonedDateTime(zonedDateTime2)
                        .diskSizeUsed(diskSizeUsed2)
                        .diskSizeTotal(diskSizeTotal2)
                        .build(),
                UserTwo.builder()
                        .name("Sora")
                        .surname("Kai")
                        .id(3L)
                        .userone(List.of(userThree))
                        .date(date3)
                        .localDateTime(localDateTime3)
                        .zonedDateTime(zonedDateTime3)
                        .diskSizeUsed(diskSizeUsed3)
                        .diskSizeTotal(diskSizeTotal3)
                        .build()
        );
    }

    public SearchCriteria getMongoSearchWrapper() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeStart = zonedDateTime1.plusDays(99);
        ZonedDateTime zonedDateTimeEnd = zonedDateTime1.plusDays(101);
        String dateStringStart = zonedDateTimeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateStringEnd = zonedDateTimeEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

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
                                                                .build(),
                                                        ComposedSearchCriteria.builder()
                                                                .logicalOperator(LogicalOperator.AND)
                                                                .innerSearchCriteria(
                                                                        List.of(
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("date")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build(),
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("localDateTime")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build()
                                                                        )
                                                                )
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

    public SearchCriteria getMySQLSearchWrapper() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeStart = zonedDateTime1.plusDays(99);
        ZonedDateTime zonedDateTimeEnd = zonedDateTime1.plusDays(101);
        String dateStringStart = zonedDateTimeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateStringEnd = zonedDateTimeEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

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
                                                                .build(),
                                                        ComposedSearchCriteria.builder()
                                                                .logicalOperator(LogicalOperator.AND)
                                                                .innerSearchCriteria(
                                                                        List.of(
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("wives.date")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .build(),
                                                        ComposedSearchCriteria.builder()
                                                                .logicalOperator(LogicalOperator.AND)
                                                                .innerSearchCriteria(
                                                                        List.of(
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("wives.localDateTime")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .build(),
                                                        ComposedSearchCriteria.builder()
                                                                .logicalOperator(LogicalOperator.AND)
                                                                .innerSearchCriteria(
                                                                        List.of(
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("wives.zonedDateTime")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("wives.diskSizeUsed")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("10")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("wives.diskSizeTotal")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("10")
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
