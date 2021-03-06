package com.mina.hanlder;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MsgHanler extends IoHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(MsgHanler.class);

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		// 出现异常
		log.error("--------exception--------");
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		// 从服务器中接收到消息后的处理
		log.info("--------msg receive--------");
		log.info("Message:{}", message.toString());
		super.messageReceived(session, message);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {// 往服务器中发送消息
		log.info("Message Send {}", message.toString());
		super.messageSent(session, message);
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// session处于空闲的时候
		log.info("当前连接{}处于空闲状态：{}", session.getRemoteAddress(), status);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// session关闭
		log.info("Session closed {}->{}", session.getId(),
				session.getRemoteAddress());
		super.sessionClosed(session);
	}
}
