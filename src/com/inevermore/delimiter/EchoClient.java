package com.inevermore.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient
{
	public void connect(int port, String host) throws Exception
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel sc)
								throws Exception
						{
							ByteBuf delimiter = Unpooled.copiedBuffer("$_"
									.getBytes());
							sc.pipeline()
									.addLast(
											new DelimiterBasedFrameDecoder(
													1024, delimiter))
									.addLast(new StringDecoder())
									.addLast(new EchoClientHandler());
						}
					});

			ChannelFuture f = b.connect(host, port).sync();

			f.channel().closeFuture().sync();

		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public static void main(String[] args) throws Exception
	{
		int port = 8989;
		new EchoClient().connect(port, "localhost");
	}
}
