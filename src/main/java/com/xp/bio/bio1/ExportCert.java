package com.xp.bio.bio1;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;

public class ExportCert {

    public static void main(String[] args) throws Exception {

        String cerPath = "/Users/xupan/IdeaProjects/springboot/doc/oauth2/xckey.crt";        //证书文件路径
        String storePath = "/Users/xupan/IdeaProjects/springboot/doc/oauth2/xc.keystore";    //证书库文件路径
        String alias = "xckey";        //证书别名
        String storePw = "xpttxsok1@23123";    //证书库密码
        String keyPw = "xpttxsok@123123";    //证书密码

        System.out.println("从证书获取的公钥为:" + getPublicKey(cerPath));
        System.out.println("从证书获取的私钥为:" + getPrivateKey(storePath, alias, storePw, keyPw));

    }

    private static String getPublicKey(String cerPath) throws Exception {
        CertificateFactory certificatefactory = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(cerPath);
        X509Certificate Cert = (X509Certificate) certificatefactory.generateCertificate(fis);
        PublicKey pk = Cert.getPublicKey();
        String publicKey = new BASE64Encoder().encode(pk.getEncoded());
        return publicKey;
    }

    private static String getPrivateKey(String storePath, String alias, String storePw, String keyPw) throws Exception {
        FileInputStream is = new FileInputStream(storePath);
        KeyStore ks = KeyStore.getInstance("JKS");
        ks.load(is, storePw.toCharArray());
        is.close();
        PrivateKey key = (PrivateKey) ks.getKey(alias, keyPw.toCharArray());
        String privateKey = new BASE64Encoder().encode(key.getEncoded());
        return privateKey;
    }



}