## run vs cmd vsentrypoint

RUN:执行命令并创建新的Image Layer
CMD:设置容器启动时默认执行的命令和参数
ENTRYPOINT：设置容器启动时运行的命令

## 格式

SHELL格式：

```dockerfile
RUN apt-get install -y vim
CMD echo "hello docker"
ENTRYPOINT echo "hello docker"
```



Excel格式：

```dockerfile
RUN ["apt-get", "install", "-y", "vim"]
CMD ["/bin/echo", "hello docker"]
ENTRYPOINT ["/bin/echo", "hello docker"]
```





Dockerfile1:

```dockerfile
FROM centos
ENV name Docker
ENTRYPOINT echo "hello $name"
```



Dockerfile2:

```dockerfile
FROM centos
ENV name Docker
ENTRYPOINT ["/bin/echo", "hello $name"]
```



docker build -t[镜像名称] -f[Dockerfile文件位置]

测试：Dockerfile1:
[root@centos7001 dockerfile-shell]# docker build -t xupan/centos-dockerfile-shell .
[root@centos7001 dockerfile-shell]# docker images
REPOSITORY                      TAG                 IMAGE ID            CREATED             SIZE
xupan/centos-dockerfile-shell   latest              bdfd6489573b        20 seconds ago      220MB
xupan/centos-vim                latest              04be75e06cd6        About an hour ago   275MB
xupan/hello-world               latest              22878ce4fd12        3 hours ago         861kB
centos                          latest              0f3e07c0138f        3 weeks ago         220MB
hello-world                     latest              fce289e99eb9        9 months ago        1.84kB
[root@centos7001 dockerfile-shell]# docker run xupan/centos-dockerfile-shell 
hello Docker



测试：Dockerfile2:
[root@centos7001 dockerfile-excel]# docker build -t xupan/centos-dockerfile-excel .
[root@centos7001 dockerfile-excel]# docker images
REPOSITORY                      TAG                 IMAGE ID            CREATED             SIZE
xupan/centos-dockerfile-excel   latest              0c840326ce2b        13 seconds ago      220MB
xupan/centos-dockerfile-shell   latest              bdfd6489573b        2 minutes ago       220MB
xupan/centos-vim                latest              04be75e06cd6        About an hour ago   275MB
xupan/hello-world               latest              22878ce4fd12        3 hours ago         861kB
centos                          latest              0f3e07c0138f        3 weeks ago         220MB
hello-world                     latest              fce289e99eb9        9 months ago        1.84kB
[root@centos7001 dockerfile-excel]# docker run xupan/centos-dockerfile-excel
hello $name

测试2并没有输出hello Docker

```dockerfile
FROM centos
ENV name Docker
ENTRYPOINT ["/bin/bash", "-c", "echo hello $name"]
```

[root@centos7001 dockerfile-excel]# docker build -t xupan/centos-dockerfile-excel .

[root@centos7001 dockerfile-excel]# docker run xupan/centos-dockerfile-excel
hello Docker





## CMD

1.CMD:设置容器启动时默认执行的命令和参数
2.如果docker run指定来其他命令，CMD命令会被忽略
3.如果定义了多个CMD，只有最后一个会生效

```dockerfile
FROM centos
ENV name Docker
CMD echo "hello $name"
```

docker run [image]     输出为hello Docker
docker run -it [image] /bin/bash 输出为? 不会打印



## ENTRYPOINT

1.让应容器以用程序或服务的形式运行
2.不会被忽略，一定执行
3.最佳实践：写一个shell脚本作为ENTRYPOINT

COPY docker-entrypoint.sh /usr/local/bin/
ENTRYPOINT ["docker-entrypoint.sh"] 
EXPOSE 27017
CMD ["mongo"]



```dockerfile
FROM centos
ENV name Docker
ENTRYPOINT echo "hello $name"
```

docker run -it [image] /bin/bash 会打印hello Docker