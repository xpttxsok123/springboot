# Container 和 image的相互转换

## 1.基于上一层container创建image(不推荐)
```text
运行一个container
[root@centos7001 ~]# docker run -it centos

进入container后做一些变化
安装一个vim
yum install -y vim

安装好以后退出
[root@5f54306f414c /]# exit
exit



这时候已经有了一个安装好vim的退出状态的container
[root@centos7001 ~]# docker container ls -a
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS                      PORTS               NAMES
5f54306f414c        centos              "/bin/bash"         5 minutes ago       Exited (0) 26 seconds ago                       youthful_solomon



通过命令把上一步的container变成一个image
Usage:  docker commit [OPTIONS] CONTAINER [REPOSITORY[:TAG]]
[root@centos7001 ~]# docker commit youthful_solomon xupan/centos-vim
sha256:1ac4ae61b1ef8e58331566abf9c0aabd51481cb9e7af5c91ca2645493f8fdae3



多了一个image,更大，因为安装了vim
[root@centos7001 ~]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
xupan/centos-vim    latest              1ac4ae61b1ef        16 seconds ago      275MB
xupan/hello-world   latest              22878ce4fd12        2 hours ago         861kB
centos              latest              0f3e07c0138f        3 weeks ago         220MB
hello-world         latest              fce289e99eb9        9 months ago        1.84kB

```


[root@centos7001 ~]# docker history centos:latest 
IMAGE               CREATED             CREATED BY                                      SIZE                COMMENT
0f3e07c0138f        3 weeks ago         /bin/sh -c #(nop)  CMD ["/bin/bash"]            0B                  
<missing>           3 weeks ago         /bin/sh -c #(nop)  LABEL org.label-schema.sc  0B                  
<missing>           3 weeks ago         /bin/sh -c #(nop) ADD file:d6fdacc1972df524a  220MB               
[root@centos7001 ~]# docker history  xupan/centos-vim:latest 
IMAGE               CREATED             CREATED BY                                      SIZE                COMMENT
1ac4ae61b1ef        6 minutes ago       /bin/bash                                       55.3MB              
0f3e07c0138f        3 weeks ago         /bin/sh -c #(nop)  CMD ["/bin/bash"]            0B                  
<missing>           3 weeks ago         /bin/sh -c #(nop)  LABEL org.label-schema.sc  0B                  
<missing>           3 weeks ago         /bin/sh -c #(nop) ADD file:d6fdacc1972df524a  220MB 

— xupan/centos-vim:latest 在原有的centos:latest多了一层1ac4ae61b1ef，共享底下3层



基于上一步运行一个image
[root@centos7001 ~]# docker run -it 1ac4ae61b1ef
[root@64605ed0f68c /]# vim



## 2.通过Dockerfile

[root@centos7001 dockerfiles]# mkdir centos-vim
[root@centos7001 dockerfiles]# cd centos-vim/
[root@centos7001 centos-vim]# ls
[root@centos7001 centos-vim]# vim Dockerfile

FROM centos:latest
RUN yum install -y vim

[root@centos7001 centos-vim]# docker build -t xupan/centos-vim  .



这个时候多了一个名为xupan/centos-vim的image
[root@centos7001 centos-vim]# docker images
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
xupan/centos-vim    latest              04be75e06cd6        8 seconds ago       275MB
xupan/hello-world   latest              22878ce4fd12        2 hours ago         861kB
centos              latest              0f3e07c0138f        3 weeks ago         220MB
hello-world         latest              fce289e99eb9        9 months ago        1.84kB



## 解析安装过程(建议)

Sending build context to Docker daemon  2.048kB
Step 1/2 : FROM centos:latest
 ---> 0f3e07c0138f
Step 2/2 : RUN yum install -y vim
 ---> Running in 2309733037c0
CentOS-8 - AppStream                            262 kB/s | 5.2 MB     00:20    
CentOS-8 - Base                                 119 kB/s | 2.2 MB     00:18    
CentOS-8 - Extras                                89  B/s | 2.1 kB     00:23

…..

Complete!
Removing intermediate container 2309733037c0
 ---> 04be75e06cd6
Successfully built 04be75e06cd6
Successfully tagged xupan/centos-vim:latest

因为image不可改变，所以docker创建了一个临时container，将临时container commit成image，complete后将临时container删除（Running in 2309733037c0，Removing intermediate container 2309733037c0）

优点：将景象给到别人时候只需要给Dockerfile