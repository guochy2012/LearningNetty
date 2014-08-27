package com.inevermore.protobuf;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubscribeRequestServer
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
							sc.pipeline()
									.addLast(new ProtobufVarint32FrameDecoder())
									.addLast(
											new ProtobufDecoder(
													SubscribeRequestProto.SubscribeRequest
															.getDefaultInstance()))
									.addLast(
											new ProtobufVarint32LengthFieldPrepender())
									.addLast(new ProtobufEncoder())
									.addLast(
											new SubscribeRequestServerHandler());
						}
					});

			//绑定端口
			ChannelFuture f = sb.bind(port).sync();
			//等待关闭
			f.channel().closeFuture().sync();
		} finally
		{
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception
	{
		int port = 8989;
		new SubscribeRequestServer().bind(port);
	}
}
