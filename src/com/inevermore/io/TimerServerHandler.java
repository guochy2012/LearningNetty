package com.inevermore.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class TimerServerHandler implements Runnable
{

	private Socket socket;

	public TimerServerHandler(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run()
	{
		BufferedReader in = null;
		PrintWriter out = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String currentTime = null;
			String body = null;
			while (true)
			{
				body = in.readLine();
				if (body == null)
					break;
				System.out.println("The time server receive body: " + body);
				if ("QUERY TIME ORDER".equalsIgnoreCase(body))
				{
					currentTime = new Date(System.currentTimeMillis())
							.toString();
				} else
				{
					currentTime = "BAD ORDER";
				}
				out.println(currentTime);//Write
				System.out.println("currentTime: " + currentTime);
			}
		} catch (Exception e)
		{
			if (in != null)
			{
				try
				{
					in.close();
					in = null;
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (out != null)
			{
				out.close();
				out = null;
			}

			if (this.socket != null)
			{
				try
				{
					this.socket.close();
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.socket = null;
			}
		}
	}
}
