package com.goldcard.nbiot.manager.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/**
 * 
 * 解码器
 * @author 1990
 *
 */
public class CommandDecoder extends ByteToMessageDecoder {
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
 
		int length = in.readableBytes();
		byte[] bs = new byte[length];
		
		while(in.isReadable()){
			in.readBytes(bs);
			out.add(bs);
		}
	}
}
