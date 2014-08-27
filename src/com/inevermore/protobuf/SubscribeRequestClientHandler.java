package com.inevermore.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

public class SubscribeRequestClientHandler extends ChannelHandlerAdapter
{
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception
	{
		for (int i = 0; i < 10; i++)
		{
			ctx.write(subReq(i));
		}
		ctx.flush();
	}

	private SubscribeRequestProto.SubscribeRequest subReq(int i)
	{
		SubscribeRequestProto.SubscribeRequest.Builder builder = SubscribeRequestProto.SubscribeRequest
				.newBuilder();
		builder.setSubReqID(i);
		builder.setUserName("haha");
		builder.setProductName("Netty Book");
		List<String> address = new ArrayList<String>();
		address.add("Beijing");
		address.add("tianjin");
		address.add("Shenzhen");
		builder.addAllAddress(address);
		return builder.build();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception
	{
		System.out.println("Receive : [" + msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
	{
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}
}
