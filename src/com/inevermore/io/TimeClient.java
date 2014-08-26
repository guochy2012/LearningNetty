package com.inevermore.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TimeClient
{
	public static void main(String[] args)
	{
		int port = 8989;

		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;

		try
		{
			socket = new Socket("localhost", port);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println("QUERY TIME ORDER");
			System.out.println("Send success!");
			String resp = in.readLine();
			System.out.println("Now is: " + resp);
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (out != null)
			{
				out.close();
				out = null;
			}
			if (in != null)
			{
				try
				{
					in.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = null;
			}

			if (socket != null)
			{
				try
				{
					socket.close();
				} catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				socket = null;
			}

		}
	}
}
