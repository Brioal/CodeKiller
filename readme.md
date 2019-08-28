# CodeKiller代码生成器
## 下载 [CodeKiller.jar](https://github.com/Brioal/CodeKiller/blob/master/CodeKiller.jar),觉得通用性不强,不同的人有不同的编码习惯,所以没打算放到插件市场
## 减少代码编写,用插件的方式生成一些不用记住但是还经常用的代码,主要针对SpringBoot
### 1.生成SpringBoot多环境配置文件
![](https://github.com/Brioal/CodeKiller/blob/master/gifs/多环境生成.gif)
### 2.切换SpringBoot当前环境
![](https://github.com/Brioal/CodeKiller/blob/master/gifs/多环境切换.gif)
### 3.生成Mysql默认连接配置
![](https://github.com/Brioal/CodeKiller/blob/master/gifs/mysql配置.gif)
### Todo
- 生成Redis默认连接配置
- 手动选择Service/Repository并且自动@AutoWrised其Set方法
- 根据Service自动生成ServiceImpl
- 自动生成实体的Set方法,主要用于类似excel导入时候大量赋值的场景
- 自动添加Swagger2依赖并且自动配置
- 从数据表生成实体
- 从实体生成Repository,Service,Controller并且实现基础的增删查改方法

