/**
 * 
 */
package com.fsgame.controllerImp;

import com.fsgame.facade.FSCommonLib;
import com.fsgame.facade.FSGameObject;
import com.fsgame.message.interfaces.MessageInterface;
import com.fsgame.mode.FSMessage;
import com.fsgame.mode.MessageHeader;
import com.fsgame.proto.MSGBaseCommon.LoginState;
import com.fsgame.proto.MSGLoginGameC2S.LoginGameC2S;
import com.fsgame.proto.MSGLoginGameS2C.LoginGameS2C;
import com.google.protobuf.InvalidProtocolBufferException;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

/**
 * @author sunweidong
 *@category 处理客户端登录请求
 *@version 2019年3月1日19:36:48
 */
public class LoginGameC2SImp implements MessageInterface{
	private ChannelHandlerContext ctx;
	private LoginGameC2S msgread = null;
	
	
	@Override
	public void readMessage(byte[] msgs, ChannelHandlerContext ctx) throws InvalidProtocolBufferException {
		this.ctx =ctx;
		msgread = LoginGameC2S.parseFrom(msgs);
		if(loginGame(msgread)) {
			writeMessage();
		}
	}

	@Override
	public void writeMessage() {
		FSMessage message = new FSMessage();
		LoginGameS2C.Builder msg = LoginGameS2C.newBuilder();
		msg.setState(LoginState._SUCESSED_);
		LoginGameS2C msgTemp = msg.build();
		
		byte[] bytes = msgTemp.toByteArray();
		
		message.setData(bytes);
		
		MessageHeader header = new MessageHeader();
		String MsgTypeStr = FSCommonLib.getKey(LoginGameS2C.class.toString());
		header.setLength(bytes.length);
		header.setMsgType(MsgTypeStr);
		message.setHeader(header);
		ctx.writeAndFlush(message);
		
	}
	
	private boolean loginGame(LoginGameC2S msgread) {
		
		//将session id加入玩家客户端列表
		Integer key = FSGameObject.Clients.size() + 1000;
		ChannelId value = ctx.channel().id();
		
		FSGameObject.Clients.put(key, value);
		return true;
	}

}
