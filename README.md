# 用户中心

时间：2023/9/04 - 2023/9/19【除部署上线】

## 第一节：上

### 企业做项目流程 

需求分析 => 设计（概要设计、详细设计）=> 技术选型 => 初始化 / 引入需要的技术 => 写 Demo => 写代码（实现业务逻辑） => 测试（单元测试）=> 代码提交 / 代码评审 => 部署=> 发布/上线

### 需求分析 

1. 登录 / 注册 【前后端跨域请求】
2. 用户管理（仅管理员可见）对用户的查询或者修改 
3. 用户校验（ 仅星球用户 ）【啊意思是加星球编号还是 ==> 意思就是数据库检验是否为星球用户，不然不登录】

### 技术选型
#### 前端

三件套 + React + 组件库 Ant Design + Umi + Ant Design Pro（现成的管理系统）

#### 后端

- java
- spring（依赖注入框架，帮助你管理 Java 对象，集成一些其他的内容）
- springmvc（web 框架，提供接口访问、restful接口等能力）
- mybatis（Java 操作数据库的框架，持久层框架，对 jdbc 的封装）
- mybatis-plus（对 mybatis 的增强，不用写 sql 也能实现增删改查）
- springboot（快速启动 / 快速集成项目。不用自己管理 spring 配置，不用自己整合各种框架）
- junit 单元测试库
- mysql

#### 部署

服务器 / 容器（平台）

### 前端初始化

#### 步骤

1. 初始化Ant Design Pro脚手架
   最好用14以上的版本 ，（ [开箱即用的中台前端/设计解决方案 - Ant Design Pro](https://pro.ant.design/zh-CN/)  ）
    按照焱总的笔记使用yarn create umi userCenter安装没成功
    <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903231340605.png" alt="image-20230903231340605" style="zoom:67%;" />

    直接使用：pro create user-center-frontend✔
    <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903231257411.png" alt="image-20230903231257411" style="zoom:67%;" />
   
``` 终端
   npm i @ant-design/pro-cli -g
   pro create 文件名   --选umi@3＆simple
   yarn
   yarn add @umijs/preset-ui -D  -- 安装umiui组件，使用前要安装yarn（见炎总笔记，感觉把能踩的bug全踩了）
```

2. 项目瘦身  【删页面文件的同时记得路由也要删掉】

   1. 删除国际化：执行 `i18n-remove `脚本
   2. 删除src/locales 
   3. 删除rc/e2e ：测试流程定义
   4. src/services/swagger：后台接口程序定义
   5. config/oneapi.json：项目接口
   6. tests：测试相关
   7. jest.config.js：测试相关配置文件
   8.  playwright.config.ts：自动测试工具
3. packge.json选择start运行，进入localhost:8000/user/login，输入账号密码跳转到了localhost:8000/welcome就说明前端初始化成功

### 后端初始化

#### 步骤

1. IDEA新建项目，选择Spring Initializr【记得修改jdk和java的版本，jdk一般选8，java】   -->   选择依赖，常见的有

   1.  `Lombok   `
   2.  `SpringBootDevTools   `
   3.  `SpringConfigurationProcessor   `
   4.  `mysqlDriver   `
   5.  `springweb  `
   6.  `junit  `
   7.  `mybatisFramework    `

2. 连接本地数据库，创建一个新的数据库，右键新建架构（顺便新建查询控制台，建个表放点数据进去

   ```sql
   -- 建表
   DROP TABLE IF EXISTS user;
   CREATE TABLE user
   (
       id BIGINT(20) NOT NULL COMMENT '主键ID',
       name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
       age INT(11) NULL DEFAULT NULL COMMENT '年龄',
       email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
       PRIMARY KEY (id)
   );
   
   -- 加数据
   DELETE FROM user;
   INSERT INTO user (id, name, age, email) VALUES
   (1, 'Jone', 18, 'test1@baomidou.com'),
   (2, 'Jack', 20, 'test2@baomidou.com'),
   (3, 'Tom', 28, 'test3@baomidou.com'),
   (4, 'Sandy', 21, 'test4@baomidou.com'),
   (5, 'Billie', 24, 'test5@baomidou.com');
   ```

   

3. 引入`mybatisbplus`依赖： pom.xml【记得刷新】

   ```xml
   <dependency>
           <groupId>com.baomidou</groupId>
           <artifactId>mybatis-plus-boot-starter</artifactId>
           <version>3.5.1</version>
   </dependency>
   ```

4. 修改`application.yml`配置

   ```yml
   # 公共配置文件
   spring:
     application:
       name: user-center
     # DataSource Config
     datasource:
       driver-class-name: com.mysql.cj.jdbc.Driver
       url: jdbc:mysql://localhost:3307/yupi
       username: root
       password: 123456
     # 设置session失效时间
     session:
       timeout: 86400
   server:
     port: 8080
     #  为了配合前端的接口，在前面都加一个/api
     servlet:
         context-path: /api
   
   mybatis-plus:
     configuration:
       map-underscore-to-camel-case: false
     global-config:
       db-config:
         logic-delete-field: isDelete # 全局逻辑删除的实体字段名
         logic-delete-value: 1 # 逻辑已删除值(默认为 1)
         logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
   ```

   

5. 添加mapper、model相关文件代码【看mybatisplus文档示例：[简介 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/24112f/)】

   ```java
   // User类  
   // @Data ≈ @Getter + @Setter + toString() + equals等方法
   @Data 
   public class User {
       private Long id;
       private String name;
       private Integer age;
       private String email;
   }
   
   // UserMapper接口
   public interface UserMapper extends BaseMapper<User> {}
   ```

   

6. SpringBoot的启动类`Application.java`添加 @MapperScan注解

   ```java
   @SpringBootApplication
   @MapperScan("com.example.usercenterbackend.Mapper")
   public class UsercenterbackendApplication {
   
       public static void main(String[] args) {
           SpringApplication.run(UsercenterbackendApplication.class, args);
       }
   
   }
   
   ```

7. 在test文件夹下添加测试类test,如果数量和数据都对的上，就说明测试成功

   ```java
   @SpringBootTest
   public class SampleTest {
     // Resource会默认按照Java的名称去注入属性
     // 但Autowired只会按照类型去注入属性，所以一般用Resource来自动注入
       @Resource
       private UserMapper userMapper;
   
       @Test
       public void testSelect() {
           System.out.println(("----- selectAll method test ------"));
           List<User> userList = userMapper.selectList(null);
           // 注意这个函数要安装junit 的依赖
           // 如果两者一致, 程序继续往下运行。如果不一致,则中断测试方法, 抛出异常信息 AssertionFailedError .
           Assert.assertEquals(5, userList.size());
           userList.forEach(System.out::println);
       }
   
   }
   ```

#### 导包相关操作

1. `http://mvnrepository.com/`这里面找
2. 选择风险点少的，人数多的
3. 复制依赖进pom.xml，再刷新就行【有时需要注意各个包之间的版本关系】

#### 一些bug

1. 注意springboot的初始化版本。一般jdk选8的话建议SpringBoot版本降低为3.0以下。**如果版本错了直接改pom.xml不行，要重新创建项目**：[Springboot配置时出现的错误——类文件具有错误的版本61.0，应为52.0_抓到一只猪的博客-CSDN博客](https://blog.csdn.net/m0_64473560/article/details/131215114?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-2-131215114-blog-128366932.235^v38^pc_relevant_anti_vip_base&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-2-131215114-blog-128366932.235^v38^pc_relevant_anti_vip_base&utm_relevant_index=1)
2. 后端测试相关注解：

   @RunWith(SpringRunner.class) 表明Test测试类要使用注入的类，比如@Autowired注入的类，有了@RunWith(SpringRunner.class)这些类才能实例化到spring容器中，自动注入才能生效

	如果@Test导入的是org.junit.jupiter.api.Test，则不要加；是org.junit.Test，则要加

<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904133423670.png" alt="image-20230904133423670" style="zoom: 50%;" />



## 第二节：中

### 后端步骤

1. 设计表

   1. 注意区分删除表（DROP）和清空表，清空的话直接去navicat右键选就行
   2. Qs:为什么转储出的sql语句再在navicat中运行是不成功的？

2. 规整项目目录，like this
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230918201827407.png" alt="image-20230918201827407" style="zoom:67%;" />

3. 自动生成器 
   1. 右键单个表，选择MybatisX-Generator，再点击一些配置
      ![image-20230918202146920](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230918202146920.png)
   2. 会生成domain、mapper、service，生成的代码会在gengerator文件夹里面，移进相应的包里面就行，注意mapper.xml的type路径需要改
      

4. 写测试类
   1. 创建测试：把光标放在Uservice上，按下alt+enter，选择创建测试，test文件夹会多一个UserSeriveTest.java

   2. 编写数据：创建好了User类，放在user上，alt+enter选择Genterate all setter with default value就会调用该对象所有的set方法，并指定默认值，注意把id的set删掉，因为本来就有默认值

   3. 增加断言：`Assertions.assertEquals(true, save);` 

      ```java
      @SpringBootTest
      public class UserServiceTest {
      
            @Resource
          private UserService userService;
      
          @Test
          public void testAddUser() {
              User user = new User();
              user.setUserName("xxz");
              user.setUserAccount("12345");
              user.setAvatarUrl("baidu.com");
              user.setGender(0);
              user.setUserPassword("123");
              user.setPhone("123123");
              user.setEmail("123");
      
              boolean save = userService.save(user);
              // 增加断言，ctrl+p可以查看需要的参数
              Assertions.assertEquals(true, save);
              System.out.println(user.getId());
          }
      }
      ```

      

5. 注册逻辑

   1. 注意在写接口的时候先写完接受参数再写注释，因为是java doc注解类型，可以直接生成参数和返回类
      ![image-20230918211046178](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230918211046178.png)

   2. 添加`apache common utils`库：很好用的库，比如说判断字符串是否为空`StringUtills.isAnyBlank(s1,s2...)`

   3. 对用户的账号、密码进行校验：账号不包含特殊字符、密码和校验密码相同等等

```java
 @Resource
    private UserMapper userMapper;
    private final String SALT = "kixuan";

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1.校验用户的账户、密码、校验密码，是否符合要求
        // 1.1.非空校验
        // 1.2. 账户长度不小于4位
        // 1.3. 密码就不小于8位
        // 1.4  星球账号不能大于5位
        if ((StringUtils.isAnyBlank(userAccount, userPassword, checkPassword))
                || (userAccount.length() < 4)
                || (userPassword.length() < 8)
                || (planetCode.length() > 5)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }

        // 1.5 账户不包含特殊字符
        String validRule = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%…… &*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validRule).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 1.6 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        // 1.7 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.DUP_INFO);
        }
        // 1.8 星球账号不能重复
         同上
           
        // 2.对密码进行MD5加密，再存进数据库（密码千万不要直接以明文存储到数据库中）
        String verifyPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes(StandardCharsets.UTF_8));
        // 3. 向数据库插入用户数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(verifyPassword);
        user.setPlanetCode(planetCode);
        int res = userMapper.insert(user);
        if (res < 0) {
            throw new BusinessException(ErrorCode.PARAM_ERROR);
        }
        return user.getId();
    }
```

      4. 直接把鼠标放在userRegister上 alt + enter，选择生成缺少的测试方式
         【TODO 这里也体现一点，每次测试都是传 入三个参数太麻烦噜，】


### 操作tips

1. 当你输入一个方法或函数的名称，并输入左括号时,按下ctrl+p，就会显示该方法或函数所接受的参数信息【我趣好好用，就是这个快捷键单手不太好按、、、比弹琴跨得还开、、、】
2.   apache common utils一个
3. 写好serviceimpl，鼠标放在方法名上，alt+enter，选择生成缺少的测试方法，会在test文件夹创建测试类
4. alt+enter选择Genterate all setter with default value就会调用该对象所有的set方法，并指定默认值，
5. alt+enter，也可以选择生成缺少的测试方式

## 第三节：下

### 后端步骤

0. 理解一下cookie和session

   1. 用户连接服务器，得到一个唯一的session

   2. 用户发送登录请求，得到这个有一些新属性的session和一个cookie（通常包含这个session的id）

   3. 只要用户在请求头带上cookie，后端根据cookie找到session

      QS：后端是怎么存储session的？数据库没有这个字段

1. 用户登录逻辑
   1. 和注册逻辑类似 , 请求参数很长时不建议用 get , 用post
   2. 用`request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser)`记录用户的登录状态
2. 用户脱敏逻辑
   1. 完全可以新建一个函数，因为很多地方用得到
   2. 传入user，不传入密码等隐私信息给safeUser
3. 新建request包
   1. 在model底下，因为本质还是实体类
   2. 这么做的好处：清晰、确保参数的合法性、减少参数数量、易于扩展、符合面向对象逻辑
```java
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -6615735376118984333L;

    private String userAccount;
    private String userPassword;
}
```

4. 登录控制层
```java
@RestController // 这个类里面所有的请求的接口返回值，响应的数据类型都是application json
@RequestMapping("/user")//定义请求的路径
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    // @RequestBody接收前端传递给后端的json字符串中的数据
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // todo 这个也好麻烦o，cszc都是直接用beanutils复制过去的
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtil.success(result);
    }
```

5. 进行测试
   1. idea自带测试工具，生成的测试在临时文件和控制台里面
   2. 感觉还挺好用的（主要是不用自己手敲url就比apifox方便( •̀ ω •́ )y
      ![image-20230910102713710](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910102713710.png)
      
   3. 填入相关url和参数，注意POST和Content-Type类型
   
    ```http
    POST http://localhost:8080/api/user/login
    Content-Type: application/json
   
    {
         "userAccount": "yupi",
        "userPassword": 12345678
    }
    ```
   
6. 用户鉴权
   1. 添加`userRole`字段可以直接再用MybatisX插件生成代码
   1. 修改其他函数的相关部分
   1.  application.yml增加session失效时间(1天)
   2. 运行前要移除之前的登录态，把target整个文件夹删掉
   3. 在测试用户权限的search的时候，要先测试login登录获得一个session，才能凭借这个session测试search，看是不是管理员，能不能进行查询


### 一些bug

1. 运行不起来，一直出现**各种Bean没注入**的错误——查看applicaition是否是小绿叶，没有的话说明没有被加载成spring的加载类，需要重新加入（[IDEA创建application.yml不是小绿叶图标（亲测有用）_idea application.yml_滑稽的鼠标的博客-CSDN博客](https://blog.csdn.net/weixin_43085797/article/details/106333243#:~:text=1、File >>Project Structure 将项目关联到spring中 2、File >>Project,Structure 将项目resources文件变成配置文件夹 选中java文件夹，点击Sources，将java文件夹变成下图所示，这样才能在java这个文件夹里面新建java.class 3、添加application.yml 4、添加启动类 在启动类上加%40SpringBootApplication注解提升没有这个包，点击下载 完美解决，application.yml变成绿叶了)）

2. 在使用Create Request in HTTP Client测试的时候出现**200-响应内容为空**的错误：查看传参字段是否和Request字段是否相同，这次是没有同意采用驼峰命名法导致字段不一样
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910091245858.png" alt="image-20230910091245858" style="zoom:67%;" />

3. Create Request in HTTP Client测试出现**status：415 --》Accept改为Content-Type**，对应POST请求
   ![image-20230910091608531](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910091608531.png)

### 操作tip

1. 写完service的函数后，在函数上面直接/**＋回车就可以快速生成注释
   ![image-20230908095808763](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908095808763.png)
2. private static final  可以先输入prsf，按下enter快速生成
3. 在service多写了参数之后，点击左侧的R可以快速在serviceimpl中添加这个参数
   ![image-20230908100947192](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908100947192.png)
4. mybatisplus可以默认帮助我们查询出来没有被删的用户：application配置 + @TableLogic配置：[逻辑删除 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/6b03c5/#使用方法)
5. 写控制器函数快速填参：插件Auto filling Java call arguments，鼠标放在括号中间alt+enter（但copilot也会自动提示(●'◡'●)
   ![image-20230908103046197](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908103046197.png)
6. `@Data`注解作用：简化一般实体类的get, set, toString，construct（构造函数）等的书写，简洁化代码。@Data≈@Getter + @Setter + toString() + equals等方法
6. ctrl+B 快速进入，fn+shift+f6重构
6. 在serviceimpl中写好了方法，再加个@Override就可以直接拉到service中
9. 常量可以专门放在一个包里面（如果是controller、service都要用的那种）
   ![image-20230910103126371](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910103126371.png)
10. 重构快捷键：shift+f6（不是这也不好按啊，机械键盘还是要两只手才按的到(〃＞目＜)
11. @RestController注解：这个类里面所有的请求的接口返回值，响应的数据类型都是application.json
    @RequestBody接收前端传递给后端的json字符串中的数据

### 前端步骤

1. 设置全局常量（新建一个ts文件啦
2. 首页修改一些页面信息
   1. src/components/Footer/index.tsx   ——修改底部版权信息
   2. src/pages/user/login ——修改logo_url、标题、删除其他的登录方式、修改登录信息
   3. 对接后台的接口——src/services/ant-design-pro/typings.d.ts修改LoginParams和后端一样，src/services/ant-design-pro/api.ts修改请求地址，config/proxy.ts配置代理地址（**注意本地是http不是https**）
   4. 后端的application.yml 指定接口全局 /api
   5. 如果f12看到login的请求是`http://locolhost:8080/api/user/login`就对噜！！

## 第四节：终

主要前端，所以笔记不多

### 前端

1. 增加注册页面
2. 定义注册地址（路由组件）
3. 删掉没用的代码
4. 提交逻辑
5. 进行测试

### 后端

1. 新增current接口获取当前登录用户信息

## 第五节：末

### 前端

1. 实现注销
2. 实现注册

### 后端

1. 增加userLogout接口实现用户登出

```java
request.getSession().removeAttribute(USER_LOGIN_STATE);
```

2. 加个星球编号字段
   1. 首先数据库加字段
   2. MybatisX重新生成
   3. 复制到model包，修改Mapper路径、service和impl、请求体都添加添加这个字段
   4. serviceimpl写业务
   5. 可能要修改测试类参数   --所以在参数多地时候就要封装，这样就不用每次多加一个参数就把所有调用地地方都改一遍
   
3. 通用返回对象
   1. 新建common包，包下新建BaseResponse类，序列化，T类型，包含code、data、message和构造方法(fn+alt+insert)
```java
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = -9210065168907605627L;
    private int code;
    private T data;
    private String message;
    private String description;


    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;

    }

    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}
```


  2. 包下新建ResultUtil类，写个success成功返回

```java
public class ResultUtil {
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok!");
    }
}
```
   3. 把controller方法的返回值都写成BaseResponse<User>

   ```java
   return ResultUtil.success(result);
   ```

  

4. 自定义错误码

   1. common下新建ErrorCode类，包含code、data、message和构造方法，注意变量要加final【声明在创建了后不能被修改，确保安全性、多线程安全和缓存】
   
   2. 定义一些常见错误枚举常量，生成get方法【注意这里不能直接加data注解，因为这是枚举类，枚举常量是有限的，每个常量都有不同的属性值，而不是通过字段赋值的方式来定义，data注解不能自动识别枚举类中的属性】
   
      ```java
      SUCCESS(0, "OK", ""),
      PARAM_ERROR(40000, "请求参数错误", ""),
      NULL_ERROR(40001, "请求参数为空", ""),
      NOT_LOGIN(40100, "未登录", ""),
      NOT_AUTH(40300, "无权限", ""),
      DUP_INFO(40400, "信息重复", ""),
      ```
   
      
   
   3. BaseResponce添加构造函数（ErrorCode），ResultUtil添加error失败返回
   
   4. 修改controller的返回类，所有的 renturn null写成throw new BusinessException(ErrorCode.枚举类)
   
   5. 让前端也显示我们的错误码：全局异常处理器


5. 封装全局异常处理
   1. ErrorCode添加一个系统内部异常的状态码
   
      ```java
      SYSTEM_ERROR(50000, "系统错误", "");
      ```
   
   2. 新建exception包，包下新建BusinessException类，序列化、final定义code，description、构造函数（写多几个，因为不确定到时候调用会给什么参数）、get方法

```java
@Getter
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1449280272362390847L;
    private final int code;
    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

}

```

   3. exception包新建GlobalExceptionHandler类
```java
   @RestControllerAdvice
   @Slf4j
   public class GlobalExceptionHandler {
       @ExceptionHandler(BusinessException.class)
       public BaseResponse handleBusinessException(BusinessException e) {
           log.info("BusinessException：{}", e.getMessage());
           return ResultUtil.error(e.getCode(), e.getMessage(), e.getDescription());
       }

       @ExceptionHandler(RuntimeException.class)
       public BaseResponse handleException(BusinessException e) {
           log.info("RuntimeException：{}", e.getMessage());
           return ResultUtil.error(ErrrCode.SYSTEM_ERROR, e.getMessage(), "");
       }
   }
```

6. 测试一下，在未登录状态输入http://localhost:8000/admin/user-manage?current=1&pageSize=5
   
   前端返回40010![image-20230915172223451](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230915172223451.png)
   后端返回
   ![image-20230915172851922](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230915172851922.png)
   就搞定了！
   
6. 总结34的关系
   
   1. 首先为什么要通用返回对象BaseResponce，一般对数据返回格式都会有个同意的要求（data），再加上状态码（code）、信息（message，具体描述返回结果）
   1. 其次一般就是success和error两种结果，所以我们再封装一次，建个ResultUtil类，以后调用的话就直接ResultUtil.error(xxxx)
   1. 那error会有很多种情况，所以我们在定义一个ErrorCode专门枚举错误码
   
8. 总结34和5的关系

   1. 为什么要封装全局异常处理
      主要目的是提高代码的可维护性、可读性和可扩展性
      把所有的异常信息集中在一个地方ErrorCode中，可以更好地管理和维护异常信息，这样报错的话就可以很容易找到是哪里出了问题，也可以比较方便地对异常信息进行修改，而不是在代码中到处跳来跳去

   2. `ErrorCode`定义错误码  -->`ResultUtil` 利用`BaseResponce`生成响应对象  --> `BusinessException` 用于标识业务异常  -->   `GlobalExceptionHandler` 用于捕获和处理这些异常并返回适当的响应


### 操作tips

1. 添加快捷输入（像sout） 设置—编辑器-实时模板—新建模板/快捷指令，类似`ResultUtil.success($END$)`，$END$就是输入快捷指令后光标在的位置；然后再define里面选上Java

## 第六节：部署【TODO】

### 多环境分类
1. 本地环境（自己的电脑）localhost
2. 开发环境（远程开发）大家连同一台机器，为了大家开发方便
3. 测试环境（测试）开发 / 测试 / 产品，单元测试 / 性能测试 / 功能测试 / 系统集成测试，独立的数据库、独立的服务器
4. 预发布环境（体验服）：和正式环境一致，正式数据库，更严谨，查出更多问题
5. 正式环境（线上，公开对外访问的项目）：尽量不要改动，保证上线前的代码是 “完美” 运行
6. 沙箱环境（实验环境）：为了做实验

参考文章：[多环境设计_程序员鱼皮的博客-CSDN博客](https://blog.csdn.net/weixin_41701290/article/details/120173283)

实现方式：通过在配置文件后面加一个后缀来区分环境

### 前端多环境配置

dev运行


bulid之后会出现如下文件大小分析，以后优化性能可以从这方面入手【等以后多学了点前端再说吧(●'◡'●)】
<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230917215929581.png" alt="image-20230917215929581" style="zoom:50%;" />

使用serve跑3000端口显示错误，奇怪捏🧐 
<img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230917200451644.png" alt="image-20230917200451644" style="zoom:50%;" />

0.0因为跑错文件夹了，终端应该是在dist文件夹下运行serve
![image-20230917200741988](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230917200741988.png)

### 后端多环境运行

1. 后端多环境主要是改：
   1. 依赖的环境地址 
   2. 数据库地址 
   3. 缓存地址 
   4. 消息队列地址 
   5. 项目端口号 
   6. 服务器配置 

**哇后面的好像没有服务器就实操不了了，这个双十一一定去搞一台来(；′⌒`)**

## 第七节：上线【TODO】

## 待补充

1. 增加一些crud
2. 项目登录改为分布式 session（单点登录 - redis）
3.  后台添加全局请求拦截器（统一去判断用户权限、统一记录请求日志）

## 没解决的问题  --都已解决

1. umi没有数据怎么回事  ——网络问题，因为他这个是从github上拉下来的【但有时候连了梯子也没有诶？奇怪🧐】
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903232604121.png" alt="image-20230903232604121" style="zoom: 33%;" />

   
   
2. idea文件夹只有md文件，同时项目结构的模块没有内容，添加整个项目文件为模块不成功
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904122212508-16938013373501.png" alt="image-20230904122212508-16938013373501" style="zoom: 50%;" />

   将【项目】改为【项目文件】就会显示
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904122249281-16938013713362.png" alt="image-20230904122249281-16938013713362" style="zoom:67%;" />

   在项目结构的模块只添加后端文佳加为模块，成功，【项目】也显示
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904133205100-16938055287873.png" alt="image-20230904133205100-16938055287873" style="zoom:67%;" />

   
   
3. @Slf4j怎么用

   哪里需要打出日志，就加注解

   ```java
   log.error("严重错误，要记监控");
   log.warn("警告一下，但是程序可以继续走");
   log.info("单纯打个日志");
   log.debug("作为一个经常写bug的人，在开发联调阶段多打点日志很正常");
   log.trace("追踪，具体还不太明白");
   ```

4. 为什么要Serializable序列化：将对象的状态转换为字节流和其他数据格式的过程，以便将其保存到文件、传输到网络或不同系统之前进行交互---》数据持久化、跨平台、安全性
   <img src="https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910101716098.png" alt="image-20230910101716098" style="zoom: 67%;" />

   
   
5. 前端用户名不显示【显示为无名】
   这个地方把username改成userName（虽然会报错但是可以就正常显示了
   ![image-20230919150953463](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230919150953463.png)