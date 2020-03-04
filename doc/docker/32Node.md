Node:拷贝node节点需要的二进制文件到node目录
[root@centos7001 bin]# scp kubelet kube-proxy root@172.16.229.172:/opt/kubernetes/bin/
[root@centos7001 bin]# scp kubelet kube-proxy root@172.16.229.171:/opt/kubernetes/bin/



生成Node需要的配置文件,在master上

```sh
# create TLS Bootstrapping Token
# BOOTSTRAP_TOKEN=$(head -c 16 /dev/urandom | od -An -t x | tr -d ' ')
# BOOTSTRAP_TOKEN=0fb61c46f8991b718eb38d27b605b008

# cat > token.csv <<EOF
# ${BOOTSTRAP_TOKEN},kubelet-bootstrap,10001,"system:kubelet-bootstrap"
# EOF

#----------------------

APISERVER=$1
SSL_DIR=$2

# create kubelet bootstrapping kubeconfig 
export KUBE_APISERVER="https://$APISERVER:6443"

# set default cluster
kubectl config set-cluster kubernetes \
  --certificate-authority=$SSL_DIR/ca.pem \
  --embed-certs=true \
  --server=${KUBE_APISERVER} \
  --kubeconfig=bootstrap.kubeconfig
  

# set client auth param
kubectl config set-credentials kubelet-bootstrap \
  --token=9691e2bc19d049dbaa169347ed096a27 \
  --kubeconfig=bootstrap.kubeconfig

# set default context
kubectl config set-context default \
  --cluster=kubernetes \
  --user=kubelet-bootstrap \
  --kubeconfig=bootstrap.kubeconfig

# set default context
kubectl config use-context default --kubeconfig=bootstrap.kubeconfig

#----------------------

# create kube-proxy kubeconfig file

kubectl config set-cluster kubernetes \
  --certificate-authority=$SSL_DIR/ca.pem \
  --embed-certs=true \
  --server=${KUBE_APISERVER} \
  --kubeconfig=kube-proxy.kubeconfig

kubectl config set-credentials kube-proxy \
  --client-certificate=$SSL_DIR/kube-proxy.pem \
  --client-key=$SSL_DIR/kube-proxy-key.pem \
  --embed-certs=true \
  --kubeconfig=kube-proxy.kubeconfig

kubectl config set-context default \
  --cluster=kubernetes \
  --user=kube-proxy \
  --kubeconfig=kube-proxy.kubeconfig

kubectl config use-context default --kubeconfig=kube-proxy.kubeconfig

```

脚本用于
bootstrap.kubeconfig: 用于请求
kube-proxy.kubeconfig: proxy组件用的



修改master01的环境变量：vim /etc/profile

```bash
export PATH=$PATH:/opt/kubernetes/bin/
```

[root@centos7001 bin]# kubectl get cs
NAME                 STATUS    MESSAGE              ERROR
scheduler            Healthy   ok                   
controller-manager   Healthy   ok                   
etcd-0               Healthy   {"health": "true"}   
etcd-2               Healthy   {"health": "true"}   
etcd-1               Healthy   {"health": "true"}   





拷贝证书到指定目录：

```bash
[root@centos7001 k8s-cert]# pwd
/usr/local/devtools/kubernetes/install/kubernetes-server-linux-amd64/k8s-cert
[root@centos7001 k8s-cert]# cp ca.pem kube-proxy.pem kube-proxy-key.pem /root/k8s/k8s-cert
```



执行脚本：172.16.229.160 /root/k8s/k8s-cert/ 分别是apiserver地址和证书存放的目录，执行脚本后会生成两个文件

```bash
[root@centos7001 kubeconfig]# sh kubeconfig.sh 172.16.229.160 /root/k8s/k8s-cert/

[root@centos7001 kubeconfig]# ls
bootstrap.kubeconfig  kubeconfig.sh  kube-proxy.kubeconfig
```



将生成的文件拷贝到指定目录下面：

```bash
[root@centos7001 kubeconfig]# scp bootstrap.kubeconfig kube-proxy.kubeconfig root@172.16.229.172:/opt/kubernetes/cfg/
root@172.16.229.172's password: 
bootstrap.kubeconfig                                                                                                    100% 2168     1.5MB/s   00:00    
kube-proxy.kubeconfig                                                                                                   100% 6270     4.0MB/s   00:00    
[root@centos7001 kubeconfig]# scp bootstrap.kubeconfig kube-proxy.kubeconfig root@172.16.229.171:/opt/kubernetes/cfg/
root@172.16.229.171's password: 
bootstrap.kubeconfig                                                                                                    100% 2168     1.8MB/s   00:00    
kube-proxy.kubeconfig                                                                                                   100% 6270     5.0MB/s   00:00    
[root@centos7001 kubeconfig]# 

```





启动kubelet：

```sh
sh kubelet.sh 172.16.229.172
```



报错：

```bash
unknow flag: bootstrap-kubeconfig
unknow flag: --config
know user
```



在master上添加用户,分配权限给kubelet

```bash
[root@centos7001 kubeconfig]# kubectl create clusterrolebinding kubelet-bootstrap --clusterrole=system:node-bootstrapper --user=kubelet-bootstrap
clusterrolebinding "kubelet-bootstrap" created
```



启动成功：

```
[root@centos7001 kubeconfig]# kubectl get csr
NAME        AGE       REQUESTOR           CONDITION
csr-wf5qb   50s       kubelet-bootstrap   Pending
[root@centos7001 kubeconfig]# 
```



给Node颁发证书:

```bash
[root@centos7001 kubeconfig]# kubectl certificate approve csr-wf5qb
certificatesigningrequest "csr-wf5qb" approved
[root@centos7001 kubeconfig]# kubectl get csr
NAME        AGE       REQUESTOR           CONDITION
csr-wf5qb   6m        kubelet-bootstrap   Approved,Issued
```



启动proxy

```bash
[root@centos7002 kubernetes-server-linux-amd64]# sh proxy.sh 172.16.229.172
```











配置Node2:

```bash
[root@centos7002 kubernetes-server-linux-amd64]# scp -r /opt/kubernetes root@172.16.229.171:/opt/

#删除多余的ssl 之后单独为node2办法证书
kubelet-client.crt  kubelet-client.key  kubelet.crt  kubelet.key
[root@centos7002 ssl]# rm -rf *
```



启动
sh kubelet.sh 172.16.229.171
sh proxy.sh 172.16.229.171





查看日志：journalctl -u kubelet --since "2019-12-13 13:30:00"

查看日志： less /var/log/messages