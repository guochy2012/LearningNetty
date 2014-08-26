package com.inevermore.nio;

public class TimeClient
{
	public static void main(String[] args)
	{
		int port = 8989;
		new Thread(new TimeClientHandle("localhost", port)).start();
	}
}
