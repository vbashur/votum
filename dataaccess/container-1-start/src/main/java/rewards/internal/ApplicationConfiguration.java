package rewards.internal;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


@Configuration
@ComponentScan("rewards.internal")
@ImportResource({"classpath:/rewards/test-infrastructure-config.xml"})
public class ApplicationConfiguration {

}
