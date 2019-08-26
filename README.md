# winter-sso-ancestor

winter-sso是一个跨域单点登录框架，只需在认证中心登录一次就可以访问其余接入系统的应用。简单配置，接入方便。可扩展认证模块。

持续更新...

![](https://github.com/WaterRRabbit/winter-sso-ancestor/blob/master/logo.png)

### 快速启动

#### 认证中心部署

`项目名称：winter-sso-server`

##### 配置信息

`文件位置：resources/application.properties`

```properties
# Redis ip
winter.redis.host=127.0.0.1
# Redis 端口
winter.redis.port=6379
```

`配置安全管理器`

```java
@Configuration
public class SecurityConfig {
    @Value("${winter.redis.host}")
    private String host;
    @Value("${winter.redis.port}")
    private String port;
    @Bean
    public SecurityManager securityManager(AuthenticatingRealm authenticatingRealm){
        DefaultSecurityManager securityManager = new DefaultSecurityManager(host, port);
        securityManager.setAuthenticatingRealm(authenticatingRealm);
        return securityManager;
    }
    @Bean
    public AuthenticatingRealm authenticatingRealm(){
        return new MemoryAuthenticatingRealm();
    }

}
```

##### 自定义认证模块

框架支持自定义认证模块，默认实现为MemoryAuthenticatingRealm（只有一个内存用户，账号：admin,密码：admin）。自定义有如下步骤：

1. 创建类实现AuthenticatingRealm接口；
2. 实现接口的doGetAuthenticationInfo方法，返回一个AuthenticationInfo对象；
3. 设置安全管理器的AuthenticatingRealm；

eg：

```java
public class MyAuthenticatingRealm extends AuthenticatingRealm {
    @Override
    AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
        // token.getPrincipal()可获取登录用户名，可根据它查找用户
        User user = .... //模拟从数据库库获取
        if(user==null){
            return null;
        }
        return new SimpleAuthenticationInfo(user.name, user.password);
    }
}
```

#### 客户端接入

`示例：winter-sso-example`

##### Maven依赖

```xml
<dependency>
    <groupId>org.winterframework</groupId>
    <artifactId>winter-sso-core</artifactId>
    <version>{版本号}</version>
</dependency>
```

##### 配置信息

```properties
# Redis ip
winter.redis.host=127.0.0.1
# Redis 端口
winter.redis.port=6379
```

##### 配置过滤器

```java
@Configuration
public class WebConfig {

    @Value("${winter.sso.server}")
    private String ssoServer;
    @Value("${winter.redis.host}")
    private String host;
    @Value("${winter.redis.port}")
    private String port;
    
    @Bean
    public FilterRegistrationBean filterRegistrationBean(SecurityManager securityManager){
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        WebSsoFilter filter = new WebSsoFilter();
        filter.setSsoServer(ssoServer);
        
        // 添加排除url，支持Ant表达式
        filter.addExcludedMapping("/test");
        filter.setSecurityManager(securityManager);
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public SecurityManager securityManager(){
        return new DefaultSecurityManager(host, port);
    }
}
```

#### 启动

* 配置host文件（模拟跨域环境）

  ```
  127.0.0.1 a.com
  127.0.0.1 b.com
  127.0.0.1 server.com
  ```

* 运行winter-sso-server和winter-sso-example项目

  ```
  1.认证中心：
  http://server.com:9999
  2.客户端a
  http://a.com:8888
  2.客户端b
  http://b.com:8888
  ```

* 认证流程

  ````
  未在认证中心登录时：
  1.访问客户端a获b，自动跳转到登录页面；
  2.登录成功后，携带Token自动跳转会原客户端；
  3.认证Token
  4.之后原客户端记住登录状态；
  已在认证中心登录，访问其它客户端时：
  1.访问其它客户端，跳转到认证中心获取用户Token；
  2.重定向到原客户端认证Token；
  3.之后原客户端记住登录状态；
  ````

  