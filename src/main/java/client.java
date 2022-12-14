

import java.net.InetSocketAddress;
import java.util.Scanner;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public class client {
    private static final int SERVER_PORT = 11011;
    private final String host;
    private final int port;

    public Channel serverChannel;
    private EventLoopGroup eventLoopGroup;
    public client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        eventLoopGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("client"));

        Bootstrap bootstrap = new Bootstrap().group(eventLoopGroup);

        bootstrap.channel(NioSocketChannel.class);
        bootstrap.remoteAddress(new InetSocketAddress(host, port));
        bootstrap.handler(new ClientInitializer());

        serverChannel = bootstrap.connect().sync().channel();
        Serverchanneladapter.serverChannel = serverChannel;
    }

    private void start() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        String message;
        ChannelFuture future;
        System.out.print("start : ");
        message = scanner.nextLine();
        int number = Integer.parseInt(message);
        // Server로 전송
        future = serverChannel.writeAndFlush(message.concat("\n"));
        while (true)
        {
            continue;
        }

        // 종료되기 전 모든 메시지가 flush 될때까지 기다림
//        if(future != null){
//            future.sync();
//        }
    }

    public void close() {
        eventLoopGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        client client = new client("127.0.0.1", SERVER_PORT);
        try {
            client.connect();
            client.start();
        } finally {
            client.close();
        }
    }

}

