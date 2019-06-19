# rabbitmq-experiments

安装 rabbitmq:

 brew install rabbitmq



创建远程访问用户:

进入rabbitmq 目录下的 plugins 目录执行添加用户,添加用户权限,设置用户角色操作

      添加用户
            ./rabbitmqctl add_user admin admin


      添加用户权限
            ./rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"


      设置用户角色
            ./rabbitmqctl set_user_tags admin administrator

         

1 安装erlang

brew install erlang 

2 安装插件

第一步： 下载插件

下载地址：http://www.rabbitmq.com/community-plugins.html

第二步：把下载好的插件解压到rabbitmq 目录下的 plugins 目录

/usr/local/Cellar/rabbitmq/3.7.3/plugins 
第三步：启动插件

rabbitmq-plugins enable rabbitmq_delayed_message_exchange


3 rabbitmq 页面新建Exchange时看到 type 多出一项 x-delayed-message 表示安装成功




