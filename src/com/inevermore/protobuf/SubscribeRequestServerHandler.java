package com.inevermore.protobuf;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubscribeRequestServerHandler extends ChannelHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception
	{
		SubscribeRequestProto.SubscribeRequest req = (SubscribeRequestProto.SubscribeRequest) msg;
		if ("haha".equalsIgnoreCase(req.getUserName()))
		{
			System.out.println("Receive req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}

	private SubscribeResponseProto.SubscribeResponse resp(int subReqID)
	{
		SubscribeResponseProto.SubscribeResponse.Builder builder = SubscribeResponseProto.SubscribeResponse
				.newBuilder();
		builder.setSubReqID(subReqID);
		builder.setRespCode(0);
		builder.setDesc("Hello World");

		return builder.build();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}
}
