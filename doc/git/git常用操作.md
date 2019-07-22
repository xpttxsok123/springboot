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



git 提交 3步

git add

git commit

git push



git 提交 2步

git ac 'xxx.txt'

git push