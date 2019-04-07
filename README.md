# SSO-demo

## 1. 创建git项目

  ```
  echo "# SSO-demo" >> README.md
  git init
  git add README.md
  git commit -m "first commit"
  git remote add origin https://github.com/CarrotWang/SSO-demo.git
  git push -u origin master
  ```
  https://www.cnblogs.com/yabin/p/6366151.html (github markdown语法)

## 2. 创建maven项目
  
  我的方式是，在Idea里创建一个新的maven项目，然后加入spring boot依赖，以及你需要加载的模块的spring boot依赖（比如mybatis）；在开发过程中，遇到了新的依赖，都在maven的pom文件中接着添加
  ```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <version>2.0.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>1.3.2</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.11</version>
        </dependency>
    </dependencies>
  ```
  
  ## 3. 基本代码编写
  + 添加java包，创建Main.java文件
  + Main类上加上Spring Boot的核心注解 @SpringBootApplication，编写Spring boot启动代码
  ```
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
  ```
  + 创建最常见的package：bean、controller、service、mapper、util等
  
  ## 4. Mybatis在Spring Boot中的使用
  + resources下创建application.properties文件，并配置数据源基本信息
  (创建数据库时记得编码设置为utf8-mb4)
```
  spring.datasource.url=jdbc:mysql://127.0.0.1:3306/sso
  spring.datasource.username=root
  spring.datasource.password=root
```
  + 在resources下创建mapper文件夹，并且在Main类上加上注解，指明mapper文件存放位置
```
  @MapperScan("carrot.mapper")
```
  我偷懒，不想去生存mapper文件，全是在mapper接口中直接写的sql。
  
  ## 5. 实现最简单的登录逻辑
  数据库表创建语句
  ```
  CREATE TABLE `user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(120) NOT NULL DEFAULT '' COMMENT 'name',
    `passwd` varchar(120) NOT NULL DEFAULT '' COMMENT 'password',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
  ```
  
  ## 6. 业务逻辑实现思路
  



  
