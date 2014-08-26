package com.inevermore.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class TimeServer
{
	public void bind(int port) throws Exception
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try
		{
			ServerBootstrap bs = new ServerBootstrap();
			bs.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024)
					.childHandler(new ChildChannelHandler());

			ChannelFuture f = bs.bind(port).sync();

			f.channel().closeFuture().sync();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel sc) throws Exception
		{
			sc.pipeline().addLast(new LineBasedFrameDecoder(1024))
					.addLast(new StringDecoder())
					.addLast(new TimeServerHandler());
		}
	}

	public static void main(String[] args)
	{
		int port = 8989;

		try
		{
			new TimeServer().bind(port);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
