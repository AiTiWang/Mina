package com.i4evercai.mina;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import android.content.Context;

import com.i4evercai.mina.bean.MsgPack;
import com.i4evercai.mina.filter.KeepAliveMessageFactoryImpl;
import com.i4evercai.mina.handler.MinaIoHandler;
import com.i4evercai.minaim.client.MinaEvent;
import com.i4evercai.minaim.client.R;

import de.greenrobot.event.EventBus;

public class MinaConnectorManager {
	private final static String HOST = "192.168.100.221";
	private final static int PORT = 28888;
	
	private static final String CLIENT_KET_PASSWORD = "123456";//私钥密码
    private static final String CLIENT_TRUST_PASSWORD = "123456";//信任证书密码
    private static final String CLIENT_AGREEMENT = "TLS";//使用协议
    private static final String CLIENT_KEY_MANAGER = "X509";//密钥管理器
    private static final String CLIENT_TRUST_MANAGER = "X509";//
    private static final String CLIENT_KEY_KEYSTORE = "BKS";//密库，这里用的是BouncyCastle密库
    private static final String CLIENT_TRUST_KEYSTORE = "BKS";//
	
	
	private volatile static MinaConnectorManager manager;
	private ExecutorService executor;
	private NioSocketConnector connector;
	private ConnectFuture connectFuture;
	private IoSession session;
	private Context context;
	private SslFilter connectorTLSFilter;
	public static MinaConnectorManager getManager(Context context) {
		if (manager == null) {
			synchronized (MinaConnectorManager.class) {
				manager = new MinaConnectorManager(context);
			}
		}
		return manager;
	}

	public MinaConnectorManager(Context context) {
		this.context = context;
		executor = Executors.newFixedThreadPool(3);
		connector = new NioSocketConnector();
		connector.setConnectTimeoutMillis(10 * 1000);
		connector.getSessionConfig().setBothIdleTime(60);
		connector.getSessionConfig().setKeepAlive(true);
		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
				IdleStatus.BOTH_IDLE);
		// 继续调用 IoHandlerAdapter 中的 sessionIdle时间
		heartBeat.setForwardEvent(false);
		// 设置心跳频率
		heartBeat.setRequestInterval(30);
		heartBeat.setRequestTimeout(10);
		
	/*	try {
			SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
			KeyManagerFactory keyManager = KeyManagerFactory.getInstance(CLIENT_KEY_MANAGER);
			TrustManagerFactory trustManager = TrustManagerFactory.getInstance(CLIENT_TRUST_MANAGER);
			//取得BKS密库实例
			KeyStore kks= KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
			KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
			//加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书
			//加客户端载证书和私钥,通过读取资源文件的方式读取密钥和信任证书
			kks.load(context
			.getResources()
			.openRawResource(R.raw.client),CLIENT_KET_PASSWORD.toCharArray());
			tks.load(context
			.getResources()
			.openRawResource(R.raw.client),CLIENT_TRUST_PASSWORD.toCharArray());
			//初始化密钥管理器
			keyManager.init(kks,CLIENT_KET_PASSWORD.toCharArray());
			trustManager.init(tks);
			//初始化SSLContext
			sslContext.init(keyManager.getKeyManagers(),trustManager.getTrustManagers(),null);
			connectorTLSFilter = new SslFilter(sslContext);
			// 设置为客户端模式
			connectorTLSFilter.setUseClientMode(true);
			connector.getFilterChain().addLast("sslFilter", connectorTLSFilter);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec",new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));     
		connector.getFilterChain().addLast("heartbeat", heartBeat);
		connector.setHandler(new MinaIoHandler(context));
	}

	public synchronized void connect() {
		System.out.println("mina***** 链接服务器~~~");
		if (isConnected()) {

		} else {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					syncConnection();
				}
			});
		}
	}

	private synchronized void syncConnection() {
		try {
			InetSocketAddress remoteSocketAddress = new InetSocketAddress(HOST, PORT);
			connectFuture = connector.connect(remoteSocketAddress);
			connectFuture.awaitUninterruptibly();
			session = connectFuture.getSession();
			EventBus.getDefault().post(new MinaEvent(1, "mina***** 链接服务器成功~~~"));
			System.out.println("mina***** 链接服务器成功~~~");
		} catch (Exception e) {
			e.printStackTrace();
			EventBus.getDefault().post(new MinaEvent(1, "mina***** 链接服务器失败~~~"));
			System.out.println("mina***** 链接服务器失败~~~");
		}

	}
	public void send(final MsgPack msg) {

		executor.execute(new Runnable() {
			@Override
			public void run() {


				if (session != null && session.isConnected()) {
					WriteFuture wf = session.write(msg.getMsgPack());
					// 消息发送超时 10秒
					wf.awaitUninterruptibly(10, TimeUnit.SECONDS);

					if (wf.isWritten()) {
						return;
					}
				} else if (session == null) {
					connect();
				}

			}
		});
	}
	public boolean isConnected() {
		if (session == null || connector == null) {
			return false;
		}
		return session.isConnected() && session.getRemoteAddress() != null
				&& connectFuture.isConnected();
	}
}
