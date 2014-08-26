package com.inevermore.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClientHandle implements Runnable
{

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;

	public TimeClientHandle(String host, int port)
	{
		this.host = (host == null) ? "127.0.0.1" : host;
		this.port = port;
		try
		{
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		try
		{
			doConnect();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			System.exit(1);
		}

		while (!stop)
		{
			try
			{
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				SelectionKey key = null;
				while (it.hasNext())
				{
					key = it.next();
					it.remove();
					try
					{
						handleInput(key);
					} catch (Exception e)
					{
						// TODO: handle exception
						if (key != null)
						{
							key.cancel();
							key.channel().close();
							key = null;
						}
					}
				}
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (selector != null)
		{
			try
			{
				selector.close();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void handleInput(SelectionKey key) throws IOException
	{
		if (key.isValid())
		{
			SocketChannel sc = (SocketChannel) key.channel();
			if (key.isConnectable())
			{
				if (sc.finishConnect())
				{
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				} else
				{
					System.exit(1);
				}
			}

			if (key.isReadable())
			{
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0)
				{
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("now is :" + body);
					this.stop = true;
				} else if (readBytes < 0)
				{
					key.cancel();
					key.channel().close();
				}
			}
		}
	}

	private void doConnect() throws IOException
	{
		if (socketChannel.connect(new InetSocketAddress(host, port)))
		{
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		} else
		{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void doWrite(SocketChannel sc) throws IOException
	{
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if (!writeBuffer.hasRemaining())
		{
			System.out.println("Send order 2 server succeed");
		}
	}

}
