package com.i4evercai.mina.filter.ssl;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;

public class BogusTrustManagerFactory extends TrustManagerFactory {

	static final X509TrustManager X509 = new X509TrustManager() {

		/**
		 * 确认和信任将其用于基于身份验证类型的客户端 SSL 身份验证
		 */
		public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

			if (x509Certificates == null || x509Certificates.length == 0)
				throw new IllegalArgumentException("null or zero-length certificate chain");
			if (s == null || s.length() == 0)
				throw new IllegalArgumentException("null or zero-length authentication type");

			boolean br = false;
			Principal principal = null;
			for (X509Certificate x509Certificate : x509Certificates) {
				principal = x509Certificate.getSubjectDN();
				if (principal != null && (StringUtils.contains(principal.getName(), "Alice") || StringUtils.contains(principal.getName(), "Bob"))) {
					br = true;
					return;
				}
			}

			if (!br) {
				throw new CertificateException("连接认证失败！");
			}
		}

		/**
		 * 确认和信任将其用于基于身份验证类型的服务器 SSL 身份验证
		 */
		public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
			if (x509Certificates == null || x509Certificates.length == 0)
				throw new IllegalArgumentException("null or zero-length certificate chain");
			if (s == null || s.length() == 0)
				throw new IllegalArgumentException("null or zero-length authentication type");

			boolean br = false;
			Principal principal = null;
			for (X509Certificate x509Certificate : x509Certificates) {
				principal = x509Certificate.getSubjectDN();
				if (principal != null && (StringUtils.contains(principal.getName(), "sundoctor.com"))) {
					br = true;
					return;
				}
			}

			if (!br) {
				throw new CertificateException("连接认证失败！");
			}
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	};

	static final TrustManager[] X509_MANAGERS = new TrustManager[] { X509 };

	public BogusTrustManagerFactory() {
		super(new BogusTrustManagerFactorySpi(), new Provider("MinaBogus", 1.0D, "") {
			private static final long serialVersionUID = -4024169055312053827L;
		}, "MinaBogus");
	}

	private static class BogusTrustManagerFactorySpi extends TrustManagerFactorySpi {

		protected TrustManager[] engineGetTrustManagers() {
			return BogusTrustManagerFactory.X509_MANAGERS;
		}

		protected void engineInit(KeyStore keystore1) throws KeyStoreException {
		}

		protected void engineInit(ManagerFactoryParameters managerfactoryparameters)
				throws InvalidAlgorithmParameterException {
		}

		private BogusTrustManagerFactorySpi() {
		}

	}

}
