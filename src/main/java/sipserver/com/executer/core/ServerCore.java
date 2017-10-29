package sipserver.com.executer.core;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.noyan.util.log.Log;

import sipserver.com.domain.ExtensionBuilder;
import sipserver.com.server.transport.UDPTransport;
import sipserver.com.service.control.ExtensionControlService;

public class ServerCore {

	// Core Element
	private static CoreElement coreElement;
	private static ServerCore serverCore;

	// Core Transport
	private UDPTransport udpTransport;

	public static void main(String[] args) throws UnknownHostException {

		Logger.getRootLogger().setLevel(Level.DEBUG);
		Logger.getRootLogger().addAppender(Log.createConsoleAppender(null));
		ServerCore.serverCore = new ServerCore();
		ServerCore.coreElement = new CoreElement();
		ServerCore.coreElement.setLocalServerAddress(InetAddress.getByName("192.168.1.107"));
		ServerCore.coreElement.setLocalSipPort(5060);

		if (args != null && args.length > 0) {
			ServerCore.coreElement.setLocalServerAddress(InetAddress.getByName(args[0]));
			if (args.length > 1) {
				ServerCore.coreElement.setLocalSipPort(Integer.valueOf(args[1]));
			}
		}

		ServerCore.serverCore.setUDPTransport(new UDPTransport());
		ServerCore.serverCore.getUDPTransport().start();
		ServerCore.getCoreElement().addLocalExtension(ExtensionBuilder.createExtension("1001", "test1001"));
		ServerCore.getCoreElement().addLocalExtension(ExtensionBuilder.createExtension("1002", "test1002"));
		ServerCore.getCoreElement().addLocalExtension(ExtensionBuilder.createExtension("1003", "test1003"));
		ServerCore.getCoreElement().addLocalExtension(ExtensionBuilder.createExtension("1004", "test1004"));
		ServerCore.getCoreElement().addLocalExtension(ExtensionBuilder.createExtension("1005", "test1005"));

		ExtensionControlService.beginControl();

		Logger.getLogger(ServerCore.class).info("Server Core Started");
	}

	public static ServerCore getServerCore() {
		return serverCore;
	}

	public UDPTransport getUDPTransport() {
		return udpTransport;
	}

	public void setUDPTransport(UDPTransport udpTransport) {
		this.udpTransport = udpTransport;
	}

	public static CoreElement getCoreElement() {
		return coreElement;
	}
}
