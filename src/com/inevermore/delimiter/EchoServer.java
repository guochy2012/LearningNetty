package com.inevermore.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer
{
	public void bind(int port) throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try
		{
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 100)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(new ChannelInitializer<SocketChannel>()
					{
						@Override
						protected void initChannel(SocketChannel sc)
								throws Exception
						{
							// TODO Auto-generated method stub
							ByteBuf delimiter = Unpooled.copiedBuffer("$_"
									.getBytes());
							sc.pipeline()
									.addLast(
											new DelimiterBasedFrameDecoder(
													1024, delimiter))
									.addLast(new StringDecoder())
									.addLast(new EchoServerHandler());
						}
					});

			ChannelFuture f = sb.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	public static void main(String[] args)
	{
		int port = 8989;
		try
		{
			new EchoServer().bind(port);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
