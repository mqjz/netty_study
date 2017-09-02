


import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferFactory;
import org.jboss.netty.buffer.ChannelBufferIndexFinder;
import org.jboss.netty.buffer.DirectChannelBufferFactory;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * Netty 服务端代码
 *
 * @author lihzh
 * @alia OneCoder
 * @blog http://www.coderli.com
 */
public class HttpServer {

    public static void main(String args[]) {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloClientHandler());
            }
        });

        bootstrap.bind(new InetSocketAddress("127.0.0.1",9000));

    }

    private static class HelloClientHandler extends SimpleChannelHandler {

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            //super.channelConnected(ctx, e);
            System.out.println("hello, I'm Server");
            String strMsg = "Hello , My Ip:" + e.getChannel().getLocalAddress().toString();
            ChannelBuffer channelBuffer = DirectChannelBufferFactory.getInstance().getBuffer(strMsg.getBytes(),0, strMsg.getBytes().length);
            e.getChannel().write(channelBuffer);
        }
    }
}
