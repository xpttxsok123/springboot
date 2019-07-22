##  1.用户认证与授权

什么是用户身份认证?

```
用户身份认证即用户去访问系统资源时系统要求验证用户的身份信息，身份合法方可继续访问。
常见的用户身份认证表现形式有:
用户名密码登录
指纹打卡
人脸识别等方式

从定义上可以看出，身份认证是为了证明用户是谁，是否为系统合法用户。
```



什么是用户授权?

```
用户认证通过后去访问系统的资源，系统会判断用户是否拥有访问资源的权限，只允许访问有权限的系统资源，没
有权限的资源将无法访问，这个过程叫用户授权。
```





## 2.第三方认证需求

```
作为互联网项目难免需要访问外部系统的资源，同样本系统也要访问第三方系统的资源接口，一个场景如下:
一个微信用户没有在学成在线注册，本系统可以通过请求微信系统来验证该用户的身份，验证通过后该用户便可在
本系统学习，它的基本流程如下:
```

```
   用户
    ↓
    ↓
 +-----------+										 		 +-----------+
 |           |		   用户登陆微信				 |           |
 |  SystemA  | ------→→→→→→→→→-------->|  WeiXin   |   微信授权            
 |           |                         |           |
 +-----------+												 +-----------+
      |                                     |
      |     认证通过获取到微信的用户信息等       |
       --------------←←←←←←←←←---------------
```

```
从上图可以看出，微信不属于SystemA，SystemA没有存储微信用户的账号、密码等信息，SystemA如果要获取该用户的基本信息则需要首先通过微信的认证系统(微信认证)进行认证，微信认证通过后SystemA便可获取该微信用户的基本信息，从而在SystemA将该微信用户的头像、昵称等信息显示出来，该用户便不用在SystemA注册却可以直接学习。
```



```
什么是第三方认证(跨平台认证)：
当需要访问第三方系统的资源时需要首先通过第三方系统的认证(例如:微信认证)，由第三方系统对用户认证通过，并授权资源的访问权限。
```

```
   用户
    ↓
    ↓
 +-----------+										 		 +-----------+
 |           |		   用户登陆微信				 |           |
 |  SystemA  | ------→→→→→→→→→-------->|  WeiXin   |   微信授权            
 |           |                         |           |
 +-----------+												 +-----------+
      |                                     
      |     请求微信用户信息              +-----------+
       --------------→→→→→→→→→-------->|           |
                                       |  WeiXin   |
                                       |           |
       																 +-----------+
```







## 3.单点登录需求

```
为了提高用户体验性需要实现用户只认证一次便可以在多个拥有访问权限的系统中访问，这个功能叫做单点登录。

引用百度百科:单点登录(Single Sign On)，简称为 SSO，是目前比较流行的企业业务整合的解决方案之一。
SSO的定义是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统。

用户登陆淘宝后，在淘宝首页可以直接跳转到天猫，聚划算，等
```

###   3.1单点登录技术分析

分布式系统要实现单点登录，通常将认证系统独立抽取出来，并且将用户身份信息存储在单独的存储介质，比如:
MySQL、Redis，考虑性能要求，通常存储在Redis中，如下图:

![dandiandenglu](images/dandiandenglu.png)

单点登录的特点是:单点登录的特点是:

```
1、认证系统为独立的系统。 
2、各子系统通过Http或其它协议与认证系统通信，完成用户认证。
3、用户身份信息存储在Redis集群。 
```



Java中有很多用户认证的框架都可以实现单点登录:

```
1、Apache Shiro
2、CAS
3、Spring security CAS 
```





## 4.Oauth2认证

```
第三方认证技术方案最主要是解决认证协议的通用标准 问题，因为要实现 跨系统认证，各系统之间要遵循一定的 接口协议。 

OAUTH协议为用户资源的授权提供了一个安全的、开放而又简易的标准。同时，任何第三方都可以使用OAUTH认 证服务，任何服务提供商都可以实现自身的OAUTH认证服务，因而OAUTH是开放的。业界提供了OAUTH的多种实 现如PHP、JavaScript，Java，Ruby等各种语言开发包，大大节约了程序员的时间，因而OAUTH是简易的。互联网 很多服务如Open API，很多大公司如Google，Yahoo，Microsoft等都提供了OAUTH认证服务，这些都足以说明 OAUTH标准逐渐成为开放资源授权的标准。 

Oauth协议目前发展到2.0版本，1.0版本过于复杂，2.0版本已得到广泛应用。 参考:https://baike.baidu.com/item/oAuth/7153134?fr=aladdin Oauth协议:https://tools.ietf.org/html/rfc6749 
```



### 4.1.Oauth2授权模式

```
授权码模式(Authorization Code) 
隐式授权模式(Implicit) 
密码模式(Resource Owner Password Credentials) 
客户端模式(Client Credentials) 
```





#### 4.1.1授权服务器生成code，颁发令牌

##### 4.1.1.0授权服务器流程

![returncode](images/authserver.png)



##### 4.1.1.1获取授权码

请求认证服务获取授权码: 

Get请求: localhost:40400/auth/oauth/authorize? 

```
   client_id=XcWebApp&response_type=code&scop=app&redirect_uri=http://localhost
```

参数列表如下:

```
 client_id:客户端id，和授权配置类中设置的客户端id一致。
 response_type:授权码模式固定为code
 scop:客户端范围，和授权配置类中设置的scop一致。
 redirect_uri:跳转uri，当授权码申请成功后会跳转到此地址，并在后边带上code参数(授权码)。 
```



首先跳转到登录页面:

![oauth_login](images/oauth_login.png)

输入账号和密码，点击Login。
Spring Security接收到请求会调用UserDetailsService接口的loadUserByUsername方法查询用户正确的密码。



接下来进入授权页面:

![getcode_url](images/getcode_url.png)





点击“同意”。
接下来返回授权码:
认证服务携带授权码跳转redirect_uri

![returncode](images/code.png)







##### 4.1.1.1获取令牌

```
拿到授权码后，申请令牌。
```

Post请求:http://localhost:40400/auth/oauth/token 

参数如下: 

```
grant_type:授权类型，填写authorization_code，表示授权码模式 
code:授权码，就是刚刚获取的授权码，注意:授权码只使用一次就无效了，需要重新申请。 
redirect_uri:申请授权码时的跳转url，一定和申请授权码时用的redirect_uri一致。
```

 此链接需要使用 http Basic认证。 

什么是http Basic认证?

http协议定义的一种认证方式，将客户端id和客户端密码按照“客户端ID:客户端密码”的格式拼接，并用base64编 码，放在header中请求服务端，一个例子: 

Authorization:Basic WGNXZWJBcHA6WGNXZWJBcHA= 

WGNXZWJBcHA6WGNXZWJBcHA= 是用户名:密码的base64编码。
认证失败服务端返回 401 Unauthorized

代码实现：

```java
//获取httpbasic的串
    private String getHttpBasic(String clientId, String clientSecret) {
        String string = clientId + ":" + clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic " + new String(encode);
    }
```



![get_token_url](images/get_token_url.png)



![garnttype](images/garnttype.png)







#### 4.1.2资源服务器授权访问

##### 4.1.2.0资源服务器流程

![returncode](images/resourceserver.png)

上图应该是公钥解密

解释：资源服务器校验令牌最终会让认证服务器校验，谁颁发谁校验

![未命名文件 (2)](images/jiaoyan.png)

1.配置公钥 

认证服务生成令牌采用非对称加密算法，认证服务采用私钥加密生成令牌，对外向资源服务提供公钥，资源服务使 用公钥 来校验令牌的合法性。 

将公钥拷贝到 publickey.txt文件中，将此文件拷贝到资源服务工程的classpath下 

![dandiandenglu](images/publickey.png)







2.添加依赖

```xml
<dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring‐cloud‐starter‐oauth2</artifactId>
</dependency>
```



3.在config包下创建ResourceServerConfig类:

```java
 @Configuration
 @EnableResourceServer
 @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
//激活方法上的    PreAuthorize注解
 public class ResourceServerConfig extends ResourceServerConfigurerAdapter { 

  //公钥
  private static final String PUBLIC_KEY = "publickey.txt"; 
   
  //定义JwtTokenStore，使用jwt令牌
  @Bean
  public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
       return new JwtTokenStore(jwtAccessTokenConverter);
  }
   
  //定义JJwtAccessTokenConverter，使用jwt令牌
  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
       JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
       converter.setVerifierKey(getPubKey());
       return converter;
  }
  
   
   /**
     * 获取非对称加密公钥 Key * @return 公钥 Key
     */
    private String getPubKey() {
        Resource resource = new ClassPathResource(PUBLIC_KEY);
        try {
           InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
          
               BufferedReader br = new BufferedReader(inputStreamReader);
               return br.lines().collect(Collectors.joining("\n"));
          
           } catch (IOException ioe) {
               return null;
           }
      }
   
//Http安全配置，对每个到达系统的http请求链接进行校验,如果加了swagger-ui，需要写额外的代码
@Override
public void configure(HttpSecurity http) throws Exception {
//所有请求必须认证通过 
  http.authorizeRequests().anyRequest().authenticated();
}
   
```





##### 4.1.2.1常见错误提示

get http://localhost:31200/course/coursepic/list/4028e58161bd3b380161bd3bcd2f0000
请求时没有携带令牌则报错:

```json
{
       "error": "unauthorized",
       "error_description": "Full authentication is required to access this resource"
}
```





请求时携带令牌:
在http header中添加 Authorization: Bearer 令牌:

![dandiandenglu](images/oktoken.png)



当输入错误的令牌也无法正常访问资源:

![dandiandenglu](images/errortoken.png)







##### 4.1.2.2常见错误提示

解决swagger-ui无法访问
当课程管理加了授权之后再访问swagger-ui则报错:

![dandiandenglu](images/swagger_uierror.png)



修改授权配置类ResourceServerConfig的configure方法: 针对swagger-ui的请求路径进行放行: 

```
//Http安全配置，对每个到达系统的http请求链接进行校验
@Override
public void configure(HttpSecurity http) throws Exception {
//所有请求必须认证通过 http.authorizeRequests()
//下边的路径放行
.antMatchers("/v2/api‐docs", "/swagger‐resources/configuration/ui",
               "/swagger‐resources","/swagger‐resources/configuration/security",
               "/swagger‐ui.html","/webjars/**").permitAll()
       .anyRequest().authenticated();
}
```



注意: 通过上边的配置虽然可以访问swagger-ui，但是无法进行单元测试，除非去掉认证的配置或在上边配置中添加所有 

请求均放行("/**")。 















#### 4.1.3案例

有道云微信登陆：

![dandiandenglu](images/disanfang_renzhen_shengmingxian.png)

oauth授权来自官网：

```
1.2.  Protocol Flow

     +--------+                               +---------------+
     |        |--(A)- Authorization Request ->|   Resource    |
     |        |                               |     Owner     |
     |        |<-(B)-- Authorization Grant ---|               |
     |        |                               +---------------+
     |        |
     |        |                               +---------------+
     |        |--(C)-- Authorization Grant -->| Authorization |
     | Client |                               |     Server    |
     |        |<-(D)----- Access Token -------|               |
     |        |                               +---------------+
     |        |
     |        |                               +---------------+
     |        |--(E)----- Access Token ------>|    Resource   |
     |        |                               |     Server    |
     |        |<-(F)--- Protected Resource ---|               |
     +--------+                               +---------------+
```





### 4.2.Oauth2密码模式

密码模式(Resource Owner Password Credentials)与授权码模式的区别是申请令牌不再使用授权码，而是直接 

通过用户名和密码即可申请令牌。
测试如下: Post请求:http://localhost:40400/auth/oauth/token 

参数: 

```
grant_type:密码模式授权填写password 
username:账号
password:密码
```


并且此链接需要使用 http Basic认证。 



![auth_xitong_jiagou](images/passworkurl.png)



![auth_xitong_jiagou](images/passwordtoken.png)

```
注意:当令牌没有过期时同一个用户再次申请令牌则不再颁发新令牌。
```



### 4.3校验令牌

Spring Security Oauth2提供校验令牌的端点，如下:
Get: http://localhost:40400/auth/oauth/check_token?token=
参数:
token:令牌

返回数据：

```json
 {
       "companyId": null,
       "userpic": null,
       "user_name": "mrt",
       "scope": ["app" ], 
       "name": null,
       "utype": null,
       "id": null,
       "exp": 1531254828,
       "jti": "6a00f227‐4c30‐47dc‐a959‐c0c147806462",
       "client_id": "XcWebApp"
   }
```



```
exp:过期时间，long类型，距离1970年的秒数(new Date().getTime()可得到当前时间距离1970年的毫秒数)。 
user_name: 用户名
client_id:客户端Id，在oauth_client_details中配置
scope:客户端范围，在oauth_client_details表中配置 
jti:与令牌对应的唯一标识
companyId、userpic、name、utype、id:这些字段是本认证服务在Spring Security基础上扩展的用户身份信息 
```







### 3.4刷新令牌 

刷新令牌是当令牌快过期时重新生成一个令牌，它于授权码授权和密码授权生成令牌不同，刷新令牌不需要授权码 

也不需要账号和密码，只需要一个刷新令牌、客户端id和客户端密码。 测试如下:
 Post:http://localhost:40400/auth/oauth/token
 参数: 

```
grant_type: 固定为 refresh_token
refresh_token:刷新令牌(注意不是access_token，而是refresh_token)
```

```
刷新令牌成功，会重新生成新的访问令牌和刷新令牌，令牌的有效期也比旧令牌长。
刷新令牌通常是在令牌快过期时进行刷新。
```



# 5.JWT Token

问题:

```
传统授权方法的问题是用户每次请求资源服务，资源服务都需要携带令牌访问认证服务去校验令牌的合法性，并根
据令牌获取用户的相关信息，性能低下。
1.认证服务器返回给客户端的token没有实际含义，资源服务器没法解析
```

![jiaoyan](images/jiaoyan.png)

解决：

```
使用JWT的思路是，用户认证通过会得到一个JWT令牌，JWT令牌中已经包括了用户相关的信息，客户端只需要携带
JWT访问资源服务，资源服务根据事先约定的算法自行完成令牌校验，无需每次都请求认证服务完成授权。

认证服务器返回给客户端的token采用jwt，资源服务器可以解析，下面的图认证服务器返回的是JWT token
```



![resourcevalidate](images/resourcevalidate.png)

什么是JWT? 

```
JSON Web Token(JWT)是一个开放的行业标准(RFC 7519)，它定义了一种简介的、自包含的协议格式，用于 在通信双方传递json对象，传递的信息经过数字签名可以被验证和信任。JWT可以使用HMAC算法或使用RSA的公 钥/私钥对来签名，防止被篡改。 
```

官网:https://jwt.io/ 

标准:https://tools.ietf.org/html/rfc7519 

JWT令牌的优点:

```
 1.jwt基于json，非常方便解析。 
 2、可以在令牌中自定义丰富的内容，易扩展。
 3、通过非对称加密算法及数字签名技术，JWT防止篡改，安全性高。
 4、资源服务使用JWT可不依赖认证服务即可完成授权。
```

 缺点:
 1.JWT令牌较长，占存储空间比较大。 



## 令牌结构

通过学习JWT令牌结构为自定义jwt令牌打好基础。 JWT令牌由三部分组成，每部分中间使用点(.)分隔，比如:xxxxx.yyyyy.zzzzz 

### 第一部分Header

 头部包括令牌的类型(即JWT)及使用的哈希算法(如HMAC SHA256或RSA) 一个例子如下:
 下边是Header部分的内容 

```json
       {
         "alg": "HS256",
         "typ": "JWT"
       }
```



将上边的内容使用Base64Url编码，得到一个字符串就是JWT令牌的第一部分。 Payload 



### 第二部分是负载

内容也是一个json对象，它是存放有效信息的地方，它可以存放jwt提供的现成字段，比 如:iss(签发者),exp(过期时间戳), sub(面向的用户)等，也可自定义字段。 

此部分不建议存放敏感信息，因为此部分可以解码还原原始内容。 最后将第二部分负载使用Base64Url编码，得到一个字符串就是JWT令牌的第二部分。 一个例子: 

```json
       {
         "sub": "1234567890",
         "name": "456",
         "admin": true
        }
```



### 第三部分是签名Signature

此部分用于防止jwt内容被篡改。 

这个部分使用base64url将前两部分进行编码，编码后使用点(.)连接组成字符串，最后使用header中声明 签名算法进行签名。 

一个例子: 

```java
       HMACSHA256(
         base64UrlEncode(header) + "." +
         base64UrlEncode(payload),
         secret)
```

base64UrlEncode(header):jwt令牌的第一部分。
base64UrlEncode(payload):jwt令牌的第二部分。
secret:签名所使用的密钥。



Spring Security 提供对JWT的支持，本节我们使用Spring Security 提供的JwtHelper来创建JWT令牌，校验JWT令牌 

等操作。





### 生成私钥和公钥 

JWT令牌生成采用非对称加密算法 

### 生成密钥证书 

下边命令生成密钥证书，采用RSA 算法每个证书包含公钥和私钥 

```shell
keytool -genkeypair -alias xckey -keyalg RSA -keypass xpttxsok@123123 -keystore xc.keystore -storepass xpttxsok1@23123
```

Keytool 是一个java提供的证书管理工具 

```
-alias:密钥的别名
-keyalg:使用的hash算法
-keypass:密钥的访问密码 
-keystore:密钥库文件名
xc.keystore保存了生成的证书 
-storepass:密钥库的访问密码

```

```java
//生成私钥和公钥
```



### 查询证书信息

```shell
keytool -list -keystore xc.keystore 
```

```shell
[@xpMac:oauth2 (master)]$ keytool -list -keystore xc.keystore
输入密钥库口令:

密钥库类型: JKS
密钥库提供方: SUN

您的密钥库包含 1 个条目

xckey, 2019-7-14, PrivateKeyEntry,
证书指纹 (SHA1): 33:40:F7:A5:20:1E:4B:BE:79:FF:BD:2F:56:2F:E9:B0:55:E8:B2:F8
```



### 删除别名

```shell
keytool -delete -alias xckey -keystore xc.keystore
```



### 查看PrivateKeyEntry

```shell
[@xpMac:oauth2 (master)]$ keytool -list -RFC -keystore  xc.keystore
输入密钥库口令:

密钥库类型: JKS
密钥库提供方: SUN

您的密钥库包含 1 个条目

别名: xckey
创建日期: 2019-7-14
条目类型: PrivateKeyEntry
证书链长度: 1
证书[1]:
-----BEGIN CERTIFICATE-----
MIIDXzCCAkegAwIBAgIEGaFT9TANBgkqhkiG9w0BAQsFADBgMREwDwYDVQQGEwh6
aG9uZ2d1bzERMA8GA1UECBMIc2hhbmdoYWkxETAPBgNVBAcTCHNoYW5naGFpMQsw
CQYDVQQKEwJ4dTELMAkGA1UECxMCeHUxCzAJBgNVBAMTAnh1MB4XDTE5MDcxNDAz
MDM1OFoXDTE5MTAxMjAzMDM1OFowYDERMA8GA1UEBhMIemhvbmdndW8xETAPBgNV
BAgTCHNoYW5naGFpMREwDwYDVQQHEwhzaGFuZ2hhaTELMAkGA1UEChMCeHUxCzAJ
BgNVBAsTAnh1MQswCQYDVQQDEwJ4dTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC
AQoCggEBAJMhiKVAB7Zyjm1JRrHeQnqcqAYgcnhyoYEdV81awlKtd2EWVHl2LV6U
wzLmMt9KdCO2Jo3jlE9Bs5VG6N6rcERD4xqdGxgMWRNMN0Ii6FJWtJRaGbWpPTVl
+BwItVOX8yT0A1OJBb8xk1lvoCqbwVMwlm+ss3FjCvV2ToYdUOyjTA8NSQJth6eD
WgzesESmlI90PXXV8vNmzh7S0jvpPQ5NuDJyasOzL28ZyIXZtinCWT3IZvOG4UL1
VGiK8uRE30Q/PNDw5zwxxcqer9Bi0L0Yz6NQ+1RhB8EVXGJj5i1qcKpXRlxcpl4w
NiGYBg8IoTJc8iXkC7OidM8sBGP1XksCAwEAAaMhMB8wHQYDVR0OBBYEFL2JRx/T
Xk6hzwO7NxOzp2ANiFM2MA0GCSqGSIb3DQEBCwUAA4IBAQA30tYGVAugHa3jRcTp
hMg1lBRykNi1kmHHStOfuEQHCkBJbTAXD14n5g0/IrXRfWtGOB5KyvxRHU44e3Ek
XhmsTrMaYYkwf5FLWHrndhRSb6Tdfjyt3WIkqsu1AJFJOpW2HMF0pTdmpslqkUYe
Xw4RDh3zkHmnVpjPwlgOTw/Q49++xmebzVh1mnX5SbVJyS0cOKMK8epC0JsSVDVG
EVHImW3eh+qpnMRMrh2FNtCN/rS9U3LZDeYfnEiiBUw9IaN3qAj++7uY6cI6iAoa
tW+3HIauxDLuU5jaZ0EKDSKpsA+DL2D0ZCPy/DaiTD8S4pINLq2mXK8IVvmeI0cD
/Xro
-----END CERTIFICATE-----


*******************************************
*******************************************
```



### 导出公钥文件

```shell
keytool -export -alias xckey -file xc.crt -keystore xc.keystore 
```

查看上一步导出的信息

```shell
[@xpMac:oauth2 (master)]$ keytool -export -alias xckey -file xckey.crt -keystore xc.keystore
输入密钥库口令:
存储在文件 <xckey.crt> 中的证书
[@xpMac:oauth2 (master)]$ keytool -printcert -file xckey.crt
所有者: CN=xu, OU=xu, O=xu, L=shanghai, ST=shanghai, C=zhongguo
发布者: CN=xu, OU=xu, O=xu, L=shanghai, ST=shanghai, C=zhongguo
序列号: 19a153f5
有效期开始日期: Sun Jul 14 11:03:58 CST 2019, 截止日期: Sat Oct 12 11:03:58 CST 2019
证书指纹:
	 MD5: A6:3C:43:DF:8E:4E:55:70:60:FD:F1:EC:58:9A:23:C1
	 SHA1: 33:40:F7:A5:20:1E:4B:BE:79:FF:BD:2F:56:2F:E9:B0:55:E8:B2:F8
	 SHA256: 7C:62:E5:87:AA:8B:5D:4E:04:67:E0:7B:39:75:75:40:7B:9C:DC:CA:C4:0D:54:AC:E0:72:8B:EB:69:6B:35:C1
	 签名算法名称: SHA256withRSA
	 版本: 3

扩展:

#1: ObjectId: 2.5.29.14 Criticality=false
SubjectKeyIdentifier [
KeyIdentifier [
0000: BD 89 47 1F D3 5E 4E A1   CF 03 BB 37 13 B3 A7 60  ..G..^N....7...`
0010: 0D 88 53 36                                        ..S6
]
]

[@xpMac:oauth2 (master)]$
```





### java代码查看私钥和公钥

```java
package com.xp.bio.bio1;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class ExportCert {

    public static void main(String[] args) throws Exception {

        String cerPath = "/Users/xupan/IdeaProjects/springboot/doc/oauth2/xckey.crt";		//证书文件路径
        String storePath = "/Users/xupan/IdeaProjects/springboot/doc/oauth2/xc.keystore";	//证书库文件路径
        String alias = "xckey";		//证书别名
        String storePw = "xpttxsok1@23123";	//证书库密码
        String keyPw = "xpttxsok@123123";	//证书密码

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

```



# 6.Spring cloud-oauth2架构设计

![auth_xitong_jiagou](images/auth_xitong_jiagou.png)

```
1、用户请求认证服务完成认证。
2、认证服务下发用户身份令牌，拥有身份令牌表示身份合法。
3、用户携带令牌请求资源服务，请求资源服务必先经过网关。 
4、网关校验用户身份令牌的合法，不合法表示用户没有登录，如果合法则放行继续访问。
5、资源服务获取令牌，资源服务器会校验令牌的合法性，完成授权。 
6、资源服务完成授权则响应资源信息。 
```



![jiaoyan](images/welogin.png)



解释：资源服务器校验令牌最终会让认证服务器校验，谁颁发谁校验

![jiaoyan](images/jiaoyan.png)







#### 



