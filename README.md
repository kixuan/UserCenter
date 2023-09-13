# 用户中心

## 第一节：上

### 企业做项目流程 

需求分析 => 设计（概要设计、详细设计）=> 技术选型 => 初始化 / 引入需要的技术 => 写 Demo => 写代码（实现业务逻辑） => 测试（单元测试）=> 代码提交 / 代码评审 => 部署=> 发布

### 需求分析 

1. 登录 / 注册 
2. 用户管理（仅管理员可见）对用户的查询或者修改 
3. 用户校验（ 仅星球用户 ）

### 前端初始化

#### 步骤

1. 初始化Ant Design Pro脚手架 -- [开箱即用的中台前端/设计解决方案 - Ant Design Pro](https://pro.ant.design/zh-CN/)  

   ``` 终端
   npm i @ant-design/pro-cli -g
   pro create 文件名   --选umi@3＆simple
   yarn
   yarn add @umijs/preset-ui -D  -- 按照umiui
   ```

2. 项目瘦身  【删页面文件的同时记得路由也要删掉】

#### 一些bug

1. yarn create umi userCenter❌
   ![image-20230903231340605](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903231340605.png)

   
   
   pro create user-center-frontend✔
   ![image-20230903231257411](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903231257411.png)

### 后端初始化

#### 步骤

1. IDEA新建项目，选择Spring Initializr【记得修改jdk和java的版本】   -->   选择依赖，常见的有 `①Lombok   ②SpringBootDevTools   ③SpringConfigurationProcessor   ④mysqlDriver   ⑤springweb  ⑥junit  ⑦mybatisFramework    `

2. 连接数据库【顺便建个表放点数据进去】
3. 修改`application.yml`配置
4. 引入`mybatisbplus`依赖： pom.xml【记得刷新】
5. 添加mapper、model相关文件代码【看mybatisplus文档示例】
6. SpringBoot的启动类`Application.java`添加 @MapperScan注解
7. 添加测试类test一下

#### 导包相关操作

1. `http://mvnrepository.com/`这里面找
2. 选择风险点少的，人数多的
3. 复制依赖进pom.xml刷新就行

#### 一些bug

1. 注意springboot的初始化版本：**如果版本错了单单改不行，要重建**——[Springboot配置时出现的错误——类文件具有错误的版本61.0，应为52.0_抓到一只猪的博客-CSDN博客](https://blog.csdn.net/m0_64473560/article/details/131215114?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-2-131215114-blog-128366932.235^v38^pc_relevant_anti_vip_base&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2~default~CTRLIST~Rate-2-131215114-blog-128366932.235^v38^pc_relevant_anti_vip_base&utm_relevant_index=1)

2. 后端测试相关注解：

​				如果@Test导入的是org.junit.jupiter.api.Test，则不要加@RunWith

​				如果导入的是org.junit.Test，则需配合 @RunWith(SpringRunner.class) 才可完成单元测试

![image-20230904133423670](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904133423670.png)



## 第二节：中

### 后端步骤

1. 设计表
2. 规整项目目录 com.yupi.usercenter-- controller、service、mapper、model、utils
3. 自动生成器 
   1. 右键model类/表，选择MybatisX-Generator，再点击一些配置
   2. 会生成domain、mapper、service，生成的代码会在gengerator文件夹里面，移进相应的包里面就行
      ![1647161384855-8dcd5b67-1d02-47d4-980c-259b542573a2](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/1647161384855-8dcd5b67-1d02-47d4-980c-259b542573a2.png)
4. 写测试类
   1. 创建测试：把光标放在Uservice上，按下alt+enter，选择创建测试，会多一个UserSeriveTes
   2. 编写数据：创建好了User类，放在user上，alt+enter选择Genterate all setter with default value就会调用该对象所有的set方法，并指定默认值，注意把id的set删掉，应为本来就有默认值
   3. 增加断言：`Assertions.assertEquals(true, save);` (当你输入一个方法或函数的名称,并输入左括号时,按下ctrl+p，就会显示该方法或函数所接受的参数信息
5. 注册逻辑
   1. 对用户的账号、密码进行校验：账号不包含特殊字符、密码和校验密码相同 【 apache common utils一个很好用的库，比如说判断字符串是否为空`StringUtills.isAnyBlank(s1,s2...)`
   2. 对密码进行MD5加密，再存进数据库
   3. 直接把鼠标放在userRegister上alt+enter，选择生成缺少的测试方式

## 第三节：下

### 后端步骤

1. 用户登录逻辑
   1. 和注册逻辑类似
   2. 用setAttribute记录用户的登录状态
2. 用户脱敏逻辑
   1. 完全可以新建一个函数，因为很多地方用得到
   2. 去除密码等个人信息
3. 新建request包
   1. 在model底下，因为本质还是实体类
   2. 这么做的好处：清晰、确保参数的合法性、减少参数数量、易于扩展、符合面向对象逻辑
   3. 为什么要序列化：将对象的状态转换为字节流或其他数据格式的过程，以便将其保存到文件、传输到网络或在不同系统之间进行交互 ---》数据持久化、跨平台、安全性
      ![image-20230910101716098](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910101716098.png)
4. 、进行测试
   1. idea自带测试工具，生成的测试在临时文件里面
   2. 感觉还挺好用的（主要是不用自己手敲url就比火狐方便
      ![image-20230910102713710](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910102713710.png)
5. 用户鉴权
   1. 添加userRole可以直接再用MybatisX插件生成代码，记得**改回UserMapper.xml的namespace和type** --todo：多去研究一下Mybatis的东西，感觉方便好多
   2. 运行前要移除之前的登录态，把target删掉
   3. 在测试用户权限的search的时候，要先测试login登录获得一个session，才能凭借这个session测试search，看是不是管理员，能不能进行查询


### 一些bug

1. 运行不起来，一直出现**各种Bean没注入**的错误——查看applicaition是否是小绿叶，没有的话说明没有被加载成spring的加载类，需要重新加入（[IDEA创建application.yml不是小绿叶图标（亲测有用）_idea application.yml_滑稽的鼠标的博客-CSDN博客](https://blog.csdn.net/weixin_43085797/article/details/106333243#:~:text=1、File >>Project Structure 将项目关联到spring中 2、File >>Project,Structure 将项目resources文件变成配置文件夹 选中java文件夹，点击Sources，将java文件夹变成下图所示，这样才能在java这个文件夹里面新建java.class 3、添加application.yml 4、添加启动类 在启动类上加%40SpringBootApplication注解提升没有这个包，点击下载 完美解决，application.yml变成绿叶了)）

2. 在使用Create Request in HTTP Client测试的时候出现**200-响应内容为空**的错误：查看传参字段是否和Request字段是否相同，这次是没有同意采用驼峰命名法导致字段不一样
   ![image-20230910091245858](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910091245858.png)

3. Create Request in HTTP Client测试出现**status：415 --》Accept改为Content-Type**，对应POST请求
   ![image-20230910091608531](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910091608531.png)

### 操作tip

1. 写完service的函数后，在函数上面直接/**＋回车就可以快速生成注释
   ![image-20230908095808763](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908095808763.png)
2. private static final  可以先输入prsf，按下enter快速生成
3. 在service多写了参数之后，点击左侧的R可以快速在serviceimpl中添加这个参数
   ![image-20230908100947192](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908100947192.png)
4. mybatisplus可以默认帮助我们查询出来没有被删的用户：application配置+@TableLogic配置。[逻辑删除 | MyBatis-Plus (baomidou.com)](https://baomidou.com/pages/6b03c5/#使用方法)
5. 写控制器函数快速填参：插件Auto filling Java call arguments，鼠标放在括号中间alt+enter
   ![image-20230908103046197](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230908103046197.png)
6. `@Data`注解作用：简化一般实体类的get, set, toString，construct（构造函数）等的书写，简洁化代码。@Data≈@Getter + @Setter + toString() + equals等方法
6. ctrl+B 快速进入
6. 在serviceimpl中写好了方法，再加个@Override就可以直接拉到service中
9. 常量可以专门放在一个包里面（如果是controller、service都要用的那种）
   ![image-20230910103126371](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230910103126371.png)
10. 重构快捷键：shift+f6（不是这也不好按啊，机械键盘还是要两只手才按的到(〃＞目＜)

### 前端步骤

1. 设置全局常量（也是新建一个ts文件啦
2. 首页修改一些页面信息
   1. src/components/Footer/index.tsx   ——修改底部版权信息
   2. src/pages/user/login ——修改logo_url、标题、删除其他的登录方式、修改登录信息
   3. 对接后台的接口——src/services/ant-design-pro/typings.d.ts修改LoginParams和后端一样，src/services/ant-design-pro/api.ts修改请求地址，config/proxy.ts配置代理地址（注意本地是http不是https）
   4. 后端的application.yml 指定接口全局 api
   5. 如果f12看到login的请求是`http://locolhost:8080/api/user/login`就对噜！！

## 第四节：终【主要前端】

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

1. 增加userLogout接口实现用户登出——removeAttrubute
2. 加个星球编号字段
   1. 首先数据库加字段
   2. MybatisX重新生成
   3. 复制到model包，修改Mapper路径、service和impl、请求体都添加添加这个字段
   4. serviceimpl写业务
   5. 可能要修改测试类参数   --所以在参数多地时候就要封装，这样就不用每次多加一个参数就把所有调用地地方都改一遍
3. 通用返回对象
   1. 新建common包，包下新建BaseResponse类，序列化，T类型，包含code、data、message和构造方法(fn+alt+insert)
   2. 包下新建ResultUtil类，写个success成功返回，return new BaseResponse<> (0,data,""ok);
   3. 把controller方法的返回值都写成BaseResponse<User>，return的话就写成 ResultUtil.success(safeUser)
4. 自定义错误码

   1. common下新建ErrorCode类，包含code、data、message和构造方法，注意变量要加final【声明在创建了后不能被修改，确保安全性、多线程安全和缓存】
   2. 定义一些常见错误枚举常量，生成get方法【注意这里不能直接加data注解，因为这是枚举类，枚举常量是有限的，每个常量都有不同的属性值，而不是通过字段赋值的方式来定义，data注解不能自动识别枚举类中的属性】
   3. BaseResponce添加构造函数（ErrorCode），ResultUtil添加error失败返回
   4. 修改controller的返回类，所有的 renturn null写成throw new BusinessException(ErrorCode.枚举类)
   5. 让前端也显示我们的错误码：全局异常处理器


5. 封装全局异常处理
   1. ErrorCode添加一个系统内部异常的状态码
   3. 新建exception包，包下新建BusinessException类，序列化、final定义code，description、构造函数（写多几个，因为不确定到时候调用会给什么参数）、get方法
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

### 操作tips

1. 添加快捷输入（像sout） 设置—编辑器-实时模板—新建模板/快捷指令，类似`ResultUtil.success($END$)`，$END$就是输入快捷指令后光标在的位置；然后再define里面选上Java

## 没解决的问题

1. umi没有数据怎么回事
   ![image-20230903232604121](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230903232604121.png)

2. idea文件夹只有md文件，同时项目结构的模块没有内容，添加整个项目文件为模块不成功
   ![image-20230904122212508-16938013373501](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904122212508-16938013373501.png)
   
   将【项目】改为【项目文件】就会显示
   ![image-20230904122249281-16938013713362](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904122249281-16938013713362.png)

   在项目结构的模块只添加后端文佳加为模块，成功，【项目】也显示
   ![image-20230904133205100-16938055287873](https://cdn.jsdelivr.net/gh/kixuan/PicGo/images/image-20230904133205100-16938055287873.png)

3. Slf4j怎么用、、、 Serializable 序列化什么用

3. 前端用户名不显示【显示为无名】