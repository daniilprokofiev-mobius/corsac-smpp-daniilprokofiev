package com.mobius.software.protocols.smpp.channel;

import com.mobius.software.common.dal.timers.CountableQueue;
import com.mobius.software.common.dal.timers.Task;
/*
 * Mobius Software LTD
 * Copyright 2019 - 2023, Mobius Software LTD and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
import com.mobius.software.protocols.smpp.Pdu;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SmppSessionWrapper extends SimpleChannelInboundHandler<Pdu> 
{
	public static final String NAME = "smppSessionWrapper";
    
	private SmppSessionChannelListener listener;
	private CountableQueue<Task> mainQueue;

	public SmppSessionWrapper(SmppSessionChannelListener listener, CountableQueue<Task> mainQueue)
	{
		this.listener = listener;
		this.mainQueue = mainQueue;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Pdu msg) throws Exception 
	{
		this.mainQueue.offerLast(new Task()
		{
			@Override
			public void execute()
			{
				SmppSessionWrapper.this.listener.firePduReceived(msg);
			}

			@Override
			public long getStartTime()
			{
				return System.currentTimeMillis();
			}
		});
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception 
	{
		this.mainQueue.offerLast(new Task()
		{
			@Override
			public void execute()
			{
				SmppSessionWrapper.this.listener.fireExceptionThrown(cause);
			}

			@Override
			public long getStartTime()
			{
				return System.currentTimeMillis();
			}
		});
	}
	 
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception 
	{
		SmppSessionWrapper.this.listener.fireChannelClosed();
	}
}