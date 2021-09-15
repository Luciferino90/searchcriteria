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
                        .name("nameOne")
                        .surname("surnameOne")
                        .date(date1)
                        .localdatetime(localDateTime1)
                        .disksizeused(diskSizeUsed1)
                        .disksizetotal(diskSizeTotal1)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build(),
                UserOneDomain.builder()
                        .name("nameOne")
                        .surname("surnameOne")
                        .date(date1)
                        .localdatetime(localDateTime1)
                        .disksizeused(diskSizeUsed1)
                        .disksizetotal(diskSizeTotal1)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(false)
                        .build(),
                UserOneDomain.builder()
                        .name("nameTwo")
                        .surname("surnameTwo")
                        .date(date1)
                        .localdatetime(localDateTime2)
                        .disksizeused(diskSizeUsed2)
                        .disksizetotal(diskSizeTotal2)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build(),
                UserOneDomain.builder()
                        .name("nameOne")
                        .surname("surnameTwo")
                        .date(date1)
                        .localdatetime(localDateTime3)
                        .disksizeused(diskSizeUsed3)
                        .disksizetotal(diskSizeTotal3)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build()
        );
    }

    public List<UserOne> getUserOne() {
        return List.of(
                UserOne.builder()
                        .name("nameOne")
                        .surname("surnameOne")
                        .id(1L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build(),
                UserOne.builder()
                        .name("nameOne")
                        .surname("surnameOne")
                        .id(2L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(false)
                        .build(),
                UserOne.builder()
                        .name("nameTwo")
                        .surname("surnameTwo")
                        .id(3L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build(),
                UserOne.builder()
                        .name("nameThree")
                        .surname("surnameThree")
                        .id(4L)
                        .enumTest(EnumTest.LABEL_ONE)
                        .active(true)
                        .build()
        );
    }

    public List<UserTwo> getUserTwo(UserOne userOne, UserOne userOneInactive, UserOne userTwo, UserOne userThree) {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTime2 = zonedDateTime1.plusDays(100);
        ZonedDateTime zonedDateTime3 = zonedDateTime1.minusDays(100);

        String dateString1 = zonedDateTime1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString2 = zonedDateTime2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dateString3 = zonedDateTime3.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

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
                        .name("nameOne")
                        .surname("surnameOne")
                        .id(1L)
                        .userOnes(List.of(userOne))
                        .date(date1)
                        .localDateTime(localDateTime1)
                        .zonedDateTime(zonedDateTime1)
                        .diskSizeUsed(diskSizeUsed1)
                        .diskSizeTotal(diskSizeTotal1)
                        .active(true)
                        .build(),
                UserTwo.builder()
                        .name("nameOne")
                        .surname("surnameOne")
                        .id(2L)
                        .userOnes(List.of(userOneInactive))
                        .date(date1)
                        .localDateTime(localDateTime1)
                        .zonedDateTime(zonedDateTime1)
                        .diskSizeUsed(diskSizeUsed1)
                        .diskSizeTotal(diskSizeTotal1)
                        .active(false)
                        .build(),
                UserTwo.builder()
                        .name("nameTwo")
                        .surname("surnameTwo")
                        .id(3L)
                        .userOnes(List.of(userTwo))
                        .date(date2)
                        .localDateTime(localDateTime2)
                        .zonedDateTime(zonedDateTime2)
                        .diskSizeUsed(diskSizeUsed2)
                        .diskSizeTotal(diskSizeTotal2)
                        .active(true)
                        .build(),
                UserTwo.builder()
                        .name("nameThree")
                        .surname("surnameThree")
                        .id(4L)
                        .userOnes(List.of(userThree))
                        .date(date3)
                        .localDateTime(localDateTime3)
                        .zonedDateTime(zonedDateTime3)
                        .diskSizeUsed(diskSizeUsed3)
                        .diskSizeTotal(diskSizeTotal3)
                        .active(true)
                        .build()
        );
    }

    public SearchCriteria getMongoSearchWrapper() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeStart = zonedDateTime1.minusDays(99);
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
                                                                .value("nameOne")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("surname")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("surnameOne")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("active")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("true")
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
                                                                .value("nameRandom1")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("nameRandom2")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                SingleSearchCriteria.builder()
                                        .field("name")
                                        .operator(SearchOperator.EQUAL)
                                        .value("nameRandom3")
                                        .build()
                        )
                )
                .build();
    }

    public SearchCriteria getMySQLSearchWrapper() {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.now();
        ZonedDateTime zonedDateTimeStart = zonedDateTime1.minusDays(99);
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
                                                                .value("nameOne")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("userTwos.name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("nameOne")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("active")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("true")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("userTwos.active")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("true")
                                                                .build(),
                                                        ComposedSearchCriteria.builder()
                                                                .logicalOperator(LogicalOperator.AND)
                                                                .innerSearchCriteria(
                                                                        List.of(
                                                                                SingleSearchCriteria.builder()
                                                                                        .field("userTwos.date")
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
                                                                                        .field("userTwos.localDateTime")
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
                                                                                        .field("userTwos.zonedDateTime")
                                                                                        .operator(SearchOperator.BETWEEN)
                                                                                        .value(dateStringStart)
                                                                                        .valueAdditional(dateStringEnd)
                                                                                        .build()
                                                                        )
                                                                )
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("userTwos.diskSizeUsed")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("1")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("userTwos.diskSizeTotal")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("1")
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
                                                                .value("nameRandom1")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("surname")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("surnameRandom1")
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
                                                                .value("nameRandom2")
                                                                .build(),
                                                        SingleSearchCriteria.builder()
                                                                .field("name")
                                                                .operator(SearchOperator.EQUAL)
                                                                .value("nameRandom3")
                                                                .build()
                                                )
                                        )
                                        .build(),
                                SingleSearchCriteria.builder()
                                        .field("name")
                                        .operator(SearchOperator.EQUAL)
                                        .value("nameRandom4")
                                        .build()
                        )
                )
                .build();
    }

}
