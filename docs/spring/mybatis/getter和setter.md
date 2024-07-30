> 一般都设置 getter 和 setter

我们研究下细分：

mapper的参数占位符的值，传给查询的对象需要 getter。[引用](mybatis.md##参数占位符的值是什么)

mapper查询结果 resultMap，也需要 setter （奇怪，其他属性没有setter也行，但是逻辑主键id不写setter就是null，写了才能查出来。但我id也没设置主键限制啊，`"id" int8 NOT NULL DEFAULT nextval('seq_teacher_id'::regclass),`）。

但VO给controller转化为json，需要 getter。