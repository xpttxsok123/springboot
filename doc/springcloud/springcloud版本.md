# Spring cloud 版本

官网：https://spring.io/projects/spring-cloud

spring cloud 有很多很多组件，如果每个组件的改动都发布一个spring cloud版本会特别麻烦，spring cloud采用了打包发布的策略，采用稳定版本发布。

打包发布的特点：将组件的改动都统一打包发布，一旦跟新就是大跟新，版本对应关系很严重

| Release Train | Boot Version |
| ------------- | ------------ |
| Greenwich     | 2.1.x        |
| Finchley      | 2.0.x        |
| Edgware       | 1.5.x        |
| Dalston       | 1.5.x        |





| Component                 | Edgware.SR6    | Finchley.SR2  | Finchley.BUILD-SNAPSHOT |
| ------------------------- | -------------- | ------------- | ----------------------- |
| spring-cloud-aws          | 1.2.4.RELEASE  | 2.0.1.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-bus          | 1.3.4.RELEASE  | 2.0.0.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-cli          | 1.4.1.RELEASE  | 2.0.0.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-commons      | 1.3.6.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-contract     | 1.2.7.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-config       | 1.4.7.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-netflix      | 1.4.7.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-security     | 1.2.4.RELEASE  | 2.0.1.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-cloudfoundry | 1.1.3.RELEASE  | 2.0.1.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-consul       | 1.3.6.RELEASE  | 2.0.1.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-sleuth       | 1.3.6.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-stream       | Ditmars.SR5    | Elmhurst.SR1  | Elmhurst.BUILD-SNAPSHOT |
| spring-cloud-zookeeper    | 1.2.3.RELEASE  | 2.0.0.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-boot               | 1.5.21.RELEASE | 2.0.6.RELEASE | 2.0.7.BUILD-SNAPSHOT    |
| spring-cloud-task         | 1.2.4.RELEASE  | 2.0.0.RELEASE | 2.0.1.BUILD-SNAPSHOT    |
| spring-cloud-vault        | 1.1.3.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-gateway      | 1.0.3.RELEASE  | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-openfeign    |                | 2.0.2.RELEASE | 2.0.2.BUILD-SNAPSHOT    |
| spring-cloud-function     | 1.0.2.RELEASE  | 1.0.0.RELEASE | 1.0.1.BUILD-SNAPSHOT    |



Finchley builds and works with Spring Boot 2.0.x, and is not expected to work with Spring Boot 1.5.x.

Note: The Dalston release train will [reach end-of-life](https://spring.io/blog/2018/06/19/spring-cloud-finchley-release-is-available) in December 2018. Edgware will follow the end-of-life cycle of Spring Boot 1.5.x.

The Dalston and Edgware release trains build on Spring Boot 1.5.x, and are not expected to work with Spring Boot 2.0.x.