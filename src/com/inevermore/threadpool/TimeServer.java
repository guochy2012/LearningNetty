package com.inevermore.threadpool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.inevermore.io.TimerServerHandler;

public class TimeServer
{

	public static void main(String[] args)
	{

		System.out.println("hello World");
		ServerSocket server = null;
		int port = 8877;

		try
		{
			server = new ServerSocket();
			server.setReuseAddress(true);
			server.bind(new InetSocketAddress(port));
			System.out.println("The Server is starting in port :" + port);
			Socket socket = null;
			
			TimeServerHandlerExcutePool pool = 
					new TimeServerHandlerExcutePool(50, 10000);
			while (true)
			{
				socket = server.accept();
				//new Thread(new TimerServerHandler(socket)).start();
				pool.execute(new TimerServerHandler(socket));
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
