
> 脏读（Dirty Read）

脏读：读未提交。

一个事务读取了另一个事务未提交的数据，如果另一个事务回滚，则读取的数据是无效的

- B更新未提交x，A读取x，B回滚，A读错。
- B增加未提交x，A读取x，B回滚，A读错。
- B删除未提交x，A读取x不存在，B回滚，A插入失败。这算脏读还是幻读，脏读，因为B未提交。


x=0，A先更新x+=1，B再读取x=1，A回滚x为=0，B读取到x就成错误的了。


> 不可重复读（Non-Repeatable Read）：

一个事务执行两次查询，第二次查询读取到了第一次查询中未提交的数据或另一个事务提交的数据

A读取x，B更新/删除/增加并提交x，A再读取x，不一致。


> 幻读（Phantom Read）

事务A中看不到晚于事务A以后其他事务提交的数据，多出的数据实际也是看不到的，但是影响在于这些实际存在的数据会影响到事务A的增删逻辑，比如你插入一条和事务B一样的内容时，会报错重复数据，但是你在事务A中又看不到这些数据。


A读取x=1不存在后就要插入它，但在这期间B插入并提交x=1，A插入就失败了。读的没有却遇到空气墙。


- RU读未提交：三害
- RC读已提交：除脏读。
    
    B更新未提交x，A扔读取x的原值；B删除未提交x，A扔能读取x；B插入未提交x，A扔读取x不存在。
- RR可重复读（默认）：再除不可重复读
    
    A读取x，B更新/删除/增加并提交x，A再读取还是原来的x。
- S串行化：再除幻读

    B插入会阻塞，直到A插入完成后，提示B插入失败 

    【读取会阻塞嘛！】

PS：脏写。
- 无论是脏写还是脏读，都是因为一个事务去更新或者查询了另外一个还没提交的事务更新过的数据

- 脏写：写了却没了

- x=0，A先更新x+=1，B再更新并提交x+=1(不管是在A更新前读x=0，还是A更新后读x=1)，A回滚x为=0

- 脏写在mysql中不存在，在update时，InnoDB存在行锁，MyISAM有表级锁