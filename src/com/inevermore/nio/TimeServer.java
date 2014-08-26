package com.inevermore.nio;

public class TimeServer
{

	public static void main(String[] args)
	{
		int port = 8989;
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		new Thread(timeServer).start();
	}
}
