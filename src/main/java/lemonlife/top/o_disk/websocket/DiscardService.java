package lemonlife.top.o_disk.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DiscardService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DiscardService.class);

    public void discard (String message) {
        LOGGER.info("丢弃消息:{}", message);
    }
}
