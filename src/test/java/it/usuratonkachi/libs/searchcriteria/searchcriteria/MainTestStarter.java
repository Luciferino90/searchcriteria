package it.usuratonkachi.libs.searchcriteria.searchcriteria;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "it.usuratonkachi")
public class MainTestStarter {

    public static void main(String[] args) {

        try {
            new SpringApplicationBuilder(MainTestStarter.class)
                    .bannerMode(Banner.Mode.OFF)
                    .web(WebApplicationType.REACTIVE)
                    .build()
                    .run(args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
