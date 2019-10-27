# image

## 1.什么是image

文件和meta data的集合（root filesystem）

分层的，并且每一层都可以改变，添加，删除，成为一个新的image

不通的image可以共享相同的layer

image本身是read-only的



## 2.docker image实现原理

典型的 Linux 在启动后，首先将 rootfs 置为 readonly，进行一系列检查, 然后将其切换为 "readwrite" 供用户使用。

在 Docker 中，起初也是将 rootfs 以 readonly 方式加载并检查，然而接下来利用 union mount 将一个 readwrite 文件系统挂载在 readonly 的 rootfs 之上，并且允许再次将下层的 file system 设定为 readonly，并且向上叠加。

这样一组 readonly 和一个 writeable 的结构构成一个 container 的运行目录，每一个被称作一个 Layer。如下图：

![image_layer](img/image_layer.png)





## 3.查看当前的image

[root@centos7001 ~]# docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
hello-world         latest              fce289e99eb9        9 months ago        1.84kB





## 4.docker image获取

1: docker pull imageName

2: dockerfile









## 5.构建第一个自己的镜像

history | grep yum

— 编译C 语言需要安装如下库
yum install gcc
yum install glibc-static



vim hello-wordc.c 

```c
#include<stdio.h>
int main()
{
printf("hello docker\n");
}
```





 将源文件编译成目标文件
[root@centos7001 hello-world]# gcc  -static hello-world.c -o hello



执行可执行文件
[root@centos7001 hello-world]# ./hello 
hello docker



[root@centos7001 hello-worldc]# vim Dockerfile
FROM scratch
ADD hello /
CMD ["/hello"]



[root@centos7001 hello-world]# docker build -t xupan/hello-world .
Sending build context to Docker daemon  864.8kB
Step 1/3 : FROM scratch
 ---> 
Step 2/3 : ADD hello /
 ---> 58da3cf4b8fc
Step 3/3 : CMD ["/hello"]
 ---> Running in 51be64b7b925
Removing intermediate container 51be64b7b925
 ---> 22878ce4fd12
Successfully built 22878ce4fd12
Successfully tagged xupan/hello-world:latest



[root@centos7001 hello-world]# docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
xupan/hello-world   latest              22878ce4fd12        26 seconds ago      861kB
hello-world         latest              fce289e99eb9        9 months ago        1.84kB



[root@centos7001 hello-world]# docker history 22878ce4fd12
IMAGE               CREATED             CREATED BY                                      SIZE                COMMENT
22878ce4fd12        2 minutes ago       /bin/sh -c #(nop)  CMD ["/hello"]               0B                  
58da3cf4b8fc        2 minutes ago       /bin/sh -c #(nop) ADD file:d9faf38ea9495d85c  861kB    





[root@centos7001 hello-world]# docker run xupan/hello-world
hello docker



## 6删除镜像

docker image rm imageID