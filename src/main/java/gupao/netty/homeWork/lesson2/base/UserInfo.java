package gupao.netty.homeWork.lesson2.base;

/**用户类*/
public class UserInfo {
	private String ip;
	private int port;
	private String name;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return ip + ":"+ port+"="+name;
	}
	
	@Override
	public boolean equals(Object oth) {
		UserInfo obj=(UserInfo)oth;
		if( this.ip.equals(obj.getIp()) && this.port==obj.getPort() )
			return true;
		else
			return false;
    }
	
	
}
