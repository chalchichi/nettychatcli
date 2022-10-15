
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg){
        String message = (String)msg;
        System.out.println(message);
        int number = Integer.parseInt(message);
        if(number>Serverchanneladapter.now)
        {
            Serverchanneladapter.now=number+2;
            String send = Integer.toString(Serverchanneladapter.now);
            Channel serverChannel = Serverchanneladapter.serverChannel;
            ChannelFuture future = serverChannel.writeAndFlush(send.concat("\n"));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

}