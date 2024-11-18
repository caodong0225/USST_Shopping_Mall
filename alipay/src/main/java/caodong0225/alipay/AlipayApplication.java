package caodong0225.alipay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author jyzxc
 * @since 2024-11-18
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AlipayApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlipayApplication.class, args);
    }

}
