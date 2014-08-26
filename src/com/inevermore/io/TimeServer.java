package com.inevermore.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TimeServer
{

	public static void main(String[] args)
	{
		System.out.println("hello World");
		ServerSocket server = null;

		try
		{
			server = new ServerSocket(8383);
			server.setReuseAddress(true);
			System.out.println("The Server is starting in port :" + 8989);
			Socket socket = null;
			while (true)
			{
				socket = server.accept();
				new Thread(new TimerServerHandler(socket)).start();
			}

		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (server != null)
			{
				try
				{
					server.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}
