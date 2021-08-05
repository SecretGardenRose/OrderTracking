
**项目结构** 
```
south-fast
├─db  项目SQL语句
│
├─common 公共模块
│  ├─aspect 系统日志
│  ├─exception 异常处理
│  ├─validator 后台校验
│  └─xss XSS过滤
│ 
├─config 配置信息
│ 
├─modules 功能模块
│  ├─app API接口模块(APP调用)
│  ├─job 定时任务模块
│  ├─oss 文件服务模块
│  └─sys 权限模块
│ 
├─SouthApplication 项目启动类
│  
├──resources 
│  ├─mapper SQL对应的XML文件
│  └─static 静态资源

```
<br> 

**技术选型：** 
- 核心框架：Spring Boot 2.1
- 安全框架：Apache Shiro 1.4
- 视图框架：Spring MVC 5.0
- 持久层框架：MyBatis 3.3
- 定时器：Quartz 2.3
- 数据库连接池：Druid 1.0
- 日志管理：SLF4J 1.7、Log4j
- 页面交互：Vue2.x 
<br> 

 **后端部署**
- 通过git下载源码
- idea、eclipse需安装lombok插件，不然会提示找不到entity的get set方法
- 创建数据库south_fast，数据库编码为UTF-8
- 执行db/mysql.sql文件，初始化数据
- 修改application-dev.yml，更新MySQL账号和密码
- Eclipse、IDEA运行RenrenApplication.java，则可启动项目
- Swagger文档路径：http://localhost:8080/south-fast/swagger/index.html
- Swagger注解路径：http://localhost:8080/south-fast/swagger-ui.html

<br> 

 **前端部署**
 - 本项目是前后端分离的，还需要部署前端，才能运行起来
 - 前端部署完毕，就可以访问项目了，账号：admin，密码：admin
 
 <br> 
 **常见问题**
 - 开发时，如何连接后台项目api接口？
 - 修改/static/config/index.js目录文件中 window.SITE_CONFIG['baseUrl'] = '本地api接口请求地址';
 

 **开发时，如何解决跨域？**
 - 修改/config/dev.env.js目录文件中OPEN_PROXY: true开启代理
 - 修改/config/index.js目录文件中proxyTable对象target: '代理api接口请求地址'
 - 重启本地服务

 **开发时，如何提前配置CDN静态资源？**
 - 修改/static/config/index-[qa/uat/prod].js目录文件中window.SITE_CONFIG['domain'] = '静态资源cdn地址';

 **构建生成后，发布需要上传哪些文件？**
 - /dist目录下：1805021549（静态资源，18年05月03日15时49分）、config（配置文件）、index.html

 **构建生成后，如何动态配置CDN静态资源？**
 - 修改/dist/config/index.js目录文件中window.SITE_CONFIG['domain'] = '静态资源cdn地址';

 **构建生成后，如何动态切换新旧版本？**
 - 修改/dist/config/index.js目录文件中 window.SITE_CONFIG['version'] = '旧版本号';
 <br>
