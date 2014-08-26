package com.inevermore.decode;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestUserInfo
{

	//这段代码比较了序列化与二进制编码后的大小
	public static void main(String[] args) throws IOException
	{
		UserInfo info = new UserInfo();
		info.buildUserID(100).buildUserName("Welcom to Netty");

		//将对象写入序列化流 
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);

		os.writeObject(info);
		os.flush();
		os.close();

		// 获取序列化之后的传输字节数组
		byte[] b = bos.toByteArray();
		System.out.println("The JDK serializable is : " + b.length);
		bos.close();

		System.out.println("------------------");

		System.out.println("The byte array serializable length is :"
				+ info.codec().length);
	}
}
