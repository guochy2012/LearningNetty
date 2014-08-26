package com.inevermore.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PerformTestUserInfo
{
	public static void main(String[] args) throws IOException
	{
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("Welcome to Netty");
		int loop = 1000000;

		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;

		//测试序列化的性能
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++)
		{
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(info);
			os.flush();
			os.close();
			bos.toByteArray();
			bos.close();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("The JDK serializable cost time is : "
				+ (endTime - startTime) + " ms");

		System.out.println("---------------------------------");

		//测试二进制编码的性能
		// ByteBuffer buffer = ByteBuffer.allocate(1024);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++)
		{
			info.codec();

		}
		endTime = System.currentTimeMillis();
		System.out.println("The byte array cost time is : "
				+ (endTime - startTime) + " ms");
	}
}
