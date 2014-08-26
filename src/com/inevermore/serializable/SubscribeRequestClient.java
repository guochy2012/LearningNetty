package com.inevermore.serializable;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubscribeRequestClient
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
							// TODO Auto-generated method stub
							sc.pipeline()
									.addLast(
											new ObjectDecoder(
													1024,
													ClassResolvers
															.cacheDisabled(this
																	.getClass()
																	.getClassLoader())))
									.addLast(new ObjectEncoder())
									.addLast(
											new SubscribeRequestClientHandler());
						}
					});

			ChannelFuture f = b.connect(host, port).sync();

			f.channel().closeFuture().sync();
		} finally
		{
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception
	{
		int port = 8989;
		new SubscribeRequestClient().connect(port, "localhost");
	}
}
