package lemonlife.top.o_disk.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Sharable
@Component
public class WebsocketMessageHandler  extends SimpleChannelInboundHandler<WebSocketFrame> {

    private ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final Logger LOGGER = LoggerFactory.getLogger(WebsocketMessageHandler.class);

    @Autowired
    DiscardService discardService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        String channelKey = ctx.channel().remoteAddress().toString();
        LOGGER.info("[im socket] send message key {}", channelKey);
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame textWebSocketFrame = (TextWebSocketFrame) msg;
            // 业务层处理数据
            this.discardService.discard(textWebSocketFrame.text());
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + System.currentTimeMillis()));
        } else if(msg instanceof BinaryWebSocketFrame) {
//           关播到多个 channel
            channelGroup.forEach(channel -> {
                if(channel != ctx.channel()){
                    msg.retain();
                    LOGGER.info("[im socket] send message to key {}", channel.remoteAddress());
                    channel.writeAndFlush(msg);
                }
            });
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        String channelKey = ctx.channel().remoteAddress().toString();
        LOGGER.info("[im socket] disConnect {}", channelKey);
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        String channelKey = ctx.channel().remoteAddress().toString();
        channelGroup.add(ctx.channel());
        LOGGER.info("[im socket] connect {}", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Channel channel = ctx.channel();
        String channelKey = ctx.channel().remoteAddress().toString();
//        if(channel.isActive()){
//            ctx.close();
//        }

//        if(ctxChannelMap.get(channelKey) !=  null){
//            ctxChannelMap.remove(channelKey);
//        }
        LOGGER.info("[im socket] exceptionCaught {}", channelKey);
    }
}
