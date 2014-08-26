package com.inevermore.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeRequestProto
{

	// 编码
	private static byte[] encode(SubscribeRequestProto.SubscribeRequest req)
	{
		return req.toByteArray();
	}

	// 解码
	private static SubscribeRequestProto.SubscribeRequest decode(byte[] body)
			throws InvalidProtocolBufferException
	{
		return SubscribeRequestProto.SubscribeRequest.parseFrom(body);
	}

	private static SubscribeRequestProto.SubscribeRequest createSubscribeRequest()
	{
		SubscribeRequestProto.SubscribeRequest.Builder builder = SubscribeRequestProto.SubscribeRequest
				.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("guochunyang");
		builder.setProductName("Netty Book");
		List<String> address = new ArrayList<String>();
		address.add("Nanjing");
		address.add("Beijing");
		address.add("Shenzhen");
		builder.addAllAddress(address);

		return builder.build();
	}

	public static void main(String[] args)
			throws InvalidProtocolBufferException
	{
		SubscribeRequestProto.SubscribeRequest req = createSubscribeRequest();
		System.out.println("Before encode : " + req.toString());
		SubscribeRequestProto.SubscribeRequest req2 = decode(encode(req));
		System.out.println("After decode : " + req2.toString());
		System.out.println("Assert equal : -----> " + req2.equals(req));
	}
}
