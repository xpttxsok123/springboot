## git

**删除远程target .idea**
```
    git rm --cached -r target/
    git rm --cached -r .idea
   
    git commit -m 'delete rm target and .idea'
    git push
```


**切换分支》》推送到远程分支**
```
git checkout -b  springboot001
git push --set-upstream origin springboot001
```





## git多账号

```
# 服务器1
#Host     别名（方便记忆）
#HostName IP地址或者域名（可以写假域名，但要在host文件中绑定ip地址）
#Port     默认是22可以不写，如果自己搭建的服务器监听的是其他端口这个参数必须声明
#IdentityFile  私钥文件路径
#User     用户名一般写git就行
#生成 ssh-keygen -t rsa -C "12345678@xx.com" -f id_rsa

Host gitee
HostName gitee.com
IdentityFile /Users/xupan/.ssh/gitee/id_rsa
User 123456789@xx.com

Host github
HostName github.com
IdentityFile /Users/xupan/.ssh/github/id_rsa
User 123456789@xx.com
```





mac下执行一下命令，让系统管理永久性的记住你所使用的私钥的

```
ssh-add -K [path/to/your/ssh-key]
如： 
[@xpMac:~]$ ssh-add -K ~/.ssh/github/id_rsa
Identity added: /Users/xupan/.ssh/github/id_rsa (18781110037@163.com)
```













git 提交 3步

git add

git commit

git push



git 提交 2步

git ac 'xxx.txt'

git push