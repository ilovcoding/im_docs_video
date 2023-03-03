package lemonlife.top.o_disk;

import lemonlife.top.o_disk.bootstrap.NettyBootStrapRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ODiskApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyBootStrapRunner.class, args);
//        SpringApplication.run(ODiskApplication.class, args);
    }
}