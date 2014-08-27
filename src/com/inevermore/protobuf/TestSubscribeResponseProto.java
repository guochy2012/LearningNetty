package com.inevermore.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeResponseProto
{

	private static byte[] encode(SubscribeResponseProto.SubscribeResponse resp)
	{
		return resp.toByteArray();
	}

	private static SubscribeResponseProto.SubscribeResponse decode(byte[] body)
			throws InvalidProtocolBufferException
	{
		return SubscribeResponseProto.SubscribeResponse.parseFrom(body);
	}

	private static SubscribeResponseProto.SubscribeResponse createSubscribeResonse()
	{
		SubscribeResponseProto.SubscribeResponse.Builder builder = SubscribeResponseProto.SubscribeResponse
				.newBuilder();
		builder.setSubReqID(1);
		builder.setRespCode(0);
		builder.setDesc("Hello World");

		return builder.build();
	}

	public static void main(String[] args)
			throws InvalidProtocolBufferException
	{
		SubscribeResponseProto.SubscribeResponse resp = createSubscribeResonse();
		System.out.println("Befor encode : " + resp.toString());
		SubscribeResponseProto.SubscribeResponse resp2 = decode(encode(resp));
		System.out.println("After decode : " + resp2.toString());
		System.out.println("Assert equals --->" + resp2.equals(resp));
	}
}
