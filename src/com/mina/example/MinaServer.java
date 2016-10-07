package com.mina.example;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.mina.charset.CharsetFactory;
import com.mina.hanlder.MsgHanler;
import com.mina.ssl.SSLContextGenerator;


public class MinaServer {

	private SocketAcceptor acceptor;

	public MinaServer() {
		/*
		 * 1.创建一个socket连接,连接到服务器
		 */
		acceptor = new NioSocketAcceptor();
	}

	public boolean start() {
		/*
		 * 获取过滤器链,用于添加过滤器
		 */
		DefaultIoFilterChainBuilder filterChain = acceptor.getFilterChain();

		/*
		 * 2.为连接添加过滤器，SSL、日志、编码过滤器
		 */
		// SSLContextGenerator是我们自己写的一个SSL上下文产生器,稍后会讲到
		// SslFilter sslFilter = new SslFilter(
		// new SSLContextGenerator().getSslContext());
		// a.ssl过滤器，这个一定要第一个添加，否则数据不会进行加密
		// filterChain.addFirst("sslFilter", sslFilter);
		// System.out.println("SSL support is added..");
		// b.添加日志过滤器
		filterChain.addLast("loger", new LoggingFilter());
		// c.添加字符的编码过滤器
		filterChain.addLast("codec", new ProtocolCodecFilter(
				new CharsetFactory()));

		/*
		 * 3.设置消息处理器，用于处理接收到的消息
		 */
		acceptor.setHandler(new MsgHanler());
		// 设置空闲的时间是30s
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
		try {
			/*
			 * 4.将服务器绑定到3456端口
			 */
			acceptor.bind(new InetSocketAddress(3456));
			System.out.println("Server is listening on port 3456..");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		MinaServer server = new MinaServer();
		server.start();
	}
}
