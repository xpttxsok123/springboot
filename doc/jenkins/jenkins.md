#Execute shell 脚本


```
#!/bin/bash
WORKSPACE = $JENKINS_HOME
echo '----------------'
echo $WORKSPACE


deleteSpringBoot=$(docker images | grep springboot |awk '{print $3}')
if [ -n "$deleteSpringBoot" ]; then
        docker rmi -f $deleteSpringBoot
        sleep 2s
fi

deleteNone=$(docker images | grep "<none>" |awk '{print $3}')
if [ -n "$deleteNone" ]; then
        docker rmi -f $deleteNone
        sleep 2s
fi


deleteTag=$(docker images | grep "registry.cn-shanghai.aliyuncs.com/jayspring/springboot" |awk '{print $3}')
if [ -n "$deleteTag" ]; then
        docker rmi -f $deleteTag
        sleep 2s
fi

cd $WORKSPACE

docker build -t springboot:latest .

docker login --username=qwerty01031 registry.cn-shanghai.aliyuncs.com

docker tag springboot registry.cn-shanghai.aliyuncs.com/jayspring/springboot:latest

docker push registry.cn-shanghai.aliyuncs.com/jayspring/springboot:latest


curl https://cs.console.aliyun.com/hook/trigger?triggerUrl=YzUyOTRkNDVmNzcxODQwM2Y5Zjg2Yjk3OTAxZTRhZDM0fHNwcmluZ2Jvb3R8cmVkZXBsb3l8MWJidHRqdTJ0OWozaXw=&secret=55464467595a454f6c557944733438432829d74fdcdccda50a17a0abdd6f9f9e

```