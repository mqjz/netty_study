import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DirectChannelBufferFactory;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class HelloClient {
    public static void main(String[] args) {
        ClientBootstrap clientBootstrap = new ClientBootstrap(new NioClientSocketChannelFactory(
                Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));

        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new HelloClientHandler());
            }
        });

        clientBootstrap.connect(new InetSocketAddress("127.0.0.1", 9000));
    }

    private static class HelloClientHandler extends SimpleChannelHandler {

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
            //super.messageReceived(ctx, e);
            if(e.getMessage() instanceof  ChannelBuffer) {
                ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
                byte[] bytes = new byte[buffer.readableBytes()];
                buffer.getBytes(0, bytes);
                System.out.println("recv: " + new String(bytes));
            }

        }

        @Override
        public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
            System.out.println("Hello ,I'm client");
        }
    }
}
