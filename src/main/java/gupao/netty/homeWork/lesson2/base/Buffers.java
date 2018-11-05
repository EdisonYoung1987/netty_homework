package gupao.netty.homeWork.lesson2.base;

import java.nio.ByteBuffer;

/**一个和socket挂钩的buffer，含有读写buffer*/
public class Buffers {
	private ByteBuffer readBuffer;
	private ByteBuffer writeBuffer;
	
	public Buffers(int readCapacity,int writeCapacity){
		this.readBuffer=ByteBuffer.allocate(readCapacity);
		this.writeBuffer=ByteBuffer.allocate(writeCapacity);
	}

	public ByteBuffer getReadBuffer() {
		return readBuffer;
	}

	public void setReadBuffer(ByteBuffer readBuffer) {
		this.readBuffer = readBuffer;
	}

	public ByteBuffer getWriteBuffer() {
		return writeBuffer;
	}

	public void setWriteBuffer(ByteBuffer writeBuffer) {
		this.writeBuffer = writeBuffer;
	}
	
}
