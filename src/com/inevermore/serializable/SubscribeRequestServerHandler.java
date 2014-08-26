package com.inevermore.serializable;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubscribeRequestServerHandler extends ChannelHandlerAdapter
{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception
	{
		SubscribeRequest req = (SubscribeRequest) msg;
		if ("guochunyang".equalsIgnoreCase(req.getUserName()))
		{
			System.out.println("Service accept client subscribe req : ["
					+ req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqID()));
		}
	}

	private SubscribeResponse resp(int subReqID)
	{
		SubscribeResponse resp = new SubscribeResponse();
		resp.setSubReqID(subReqID);
		resp.setRespCode(0);
		resp.setDesc("Netty book order succeed, 3 days later, send to ....");
		return resp;
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception
	{
		cause.printStackTrace();
		ctx.close();
	}
}
