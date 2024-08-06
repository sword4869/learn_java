## 50题

[【sql学习】经典面试50题-PostgreSQL语句练习 - 檬子sama～ - 博客园 (cnblogs.com)](https://www.cnblogs.com/mengzisama/p/13363541.html)

```sql
create table student(
    s_id varchar(10),
    s_name varchar(20),
    s_age date,
    s_sex varchar(10)
);

create table course(
    c_id varchar(10),
    c_name varchar(20),
    t_id varchar(10)
);


create table teacher (
t_id varchar(10),
t_name varchar(20)
);

create table score (
    s_id varchar(10),
    c_id varchar(10),
    score integer
);


insert into student (s_id, s_name, s_age, s_sex)
values  ('01' , '赵雷' , '1990-01-01' , '男'),
        ('02' , '钱电' , '1990-12-21' , '男'),
        ('03' , '孙风' , '1990-05-20' , '男'),
        ('04' , '李云' , '1990-08-06' , '男'),
        ('05' , '周梅' , '1991-12-01' , '女'),
        ('06' , '吴兰' , '1992-03-01' , '女'),
        ('07' , '郑竹' , '1989-07-01' , '女'),
        ('08' , '王菊' , '1990-01-20' , '女');

insert into course (c_id, c_name, t_id)
values  ('01' , '语文' , '02'),
        ('02' , '数学' , '01'),
        ('03' , '英语' , '03');

insert into teacher (t_id, t_name)
values  ('01' , '张三'),
        ('02' , '李四'),
        ('03' , '王五');

insert into score (s_id, c_id, score)
values  ('01' , '01' , 80),
        ('01' , '02' , 90),
        ('01' , '03' , 99),
        ('02' , '01' , 70),
        ('02' , '02' , 60),
        ('02' , '03' , 80),
        ('03' , '01' , 80),
        ('03' , '02' , 80),
        ('03' , '03' , 80),
        ('04' , '01' , 50),
        ('04' , '02' , 30),
        ('04' , '03' , 20),
        ('05' , '01' , 76),
        ('05' , '02' , 87),
        ('06' , '01' , 31),
        ('06' , '03' , 34),
        ('07' , '02' , 89),
        ('07' , '03' , 98);
				
				
-- 学生的名单跟成绩名单相比，学生的id是多的，也就是说8号学生是没有成绩的，另外也发现有的学生的部分课程没有成绩



-- 1、查询"01"课程比"02"课程成绩高的学生的学号及课程分数（重点）

select s1.s_id, s1.score '01', s2.score '02'
from score s1 join score s2 on s1.s_id = s2.s_id
where s1.c_id = '01' and s2.c_id = '02' and s1.score > s2.score;


-- 2、查询平均成绩大于60分的学生的学号和平均成绩（重点）

SELECT s_id, AVG(score) avg_score
from score 
GROUP BY s_id
having avg_score > 60

-- 3、查询所有同学的学号、姓名、选课数、总成绩

SELECT s.s_id, s.s_name, s1.s_count, s1.s_sum
from 
student s left join
(
	SELECT s_id, COUNT(s_id) s_count, sum(score) s_sum
	from score
	GROUP BY s_id
) s1 
on s.s_id = s1.s_id


-- 第四题：查询姓“李”的老师的个数；

SELECT count(1)
FROM teacher
where t_name like '李%'


-- 第五题：查询没学过“张三”老师课的学生的学号、姓名（重点）

select s.s_id, s.s_name
from student s
where s.s_id not in 
(
	SELECT s2.s_id
	from score s2 join course c join teacher t 
	on s2.c_id = c.c_id and c.t_id = t.t_id
	where t.t_name = '张三'
)



-- 第六题：查询学过“张三”老师所教的所有课的同学的学号、姓名（重点）

select s.s_id, s.s_name
from student s
where s.s_id in 
(
	SELECT s2.s_id
	from score s2 join course c join teacher t 
	on s2.c_id = c.c_id and c.t_id = t.t_id
	where t.t_name = '张三'
)


-- 7、查询学过编号为“01”的课程并且也学过编号为“02”的课程的学生的学号、姓名（重点）

SELECT s.s_id, s.s_name
from score s1 join score s2 on s1.s_id = s2.s_id join student s on s.s_id = s2.s_id
where s1.c_id = '01' and s2.c_id = '02' 


-- 8、查询课程编号为“02”的总成绩（不重点）

select sum(s.score)
from score s
GROUP BY s.c_id 
HAVING s.c_id = '02'

SELECT sum(score)
from score
where c_id = '02'


-- 9、查询所有课程成绩小于60分的学生的学号、姓名（同第2题，要注意存在成绩为null的情况，因此得采用not in）【反选】
 

-- 错误写法：正选，最大成绩小于60的。但成绩为null的，就会放过。
-- SELECT st.s_id, st.s_name
-- from student st 
-- where st.s_id in 
-- (
-- 	SELECT s.s_id
-- 	from score s 
-- 	GROUP BY s.s_id
-- 	having max(s.score) < 60
-- )

-- 正确：反选成绩都大于的
SELECT st.s_id, st.s_name	
from student st 
where st.s_id not in 
(
	SELECT s.s_id
	from score s 
	GROUP BY s.s_id
	having max(s.score) > 60
)


-- 10、查询没有学全所有课的学生的学号、姓名(重点)【反选】

select st.s_id, st.s_name
from student st
where st.s_id not in
(
	select s.s_id
	from score s
	GROUP BY s.s_id 
	having count(s.score) = (SELECT count(1) from course)
)

-- 11、查询至少有一门课与学号为“01”的学生所学课程相同的学生的学号和姓名（重点）

select st.s_id, st.s_name
from student st
where st.s_id in
(
	SELECT DISTINCT s.s_id
	from score s
	where s.c_id in (SELECT s2.c_id from score s2 where s2.s_id = '01') and s.s_id != '01'
)

-- 12.查询和“01”号同学所学课程完全相同的其他同学的学号(重点)【better】

SELECT s.s_id
from score s
where s.s_id != '01'
GROUP BY s.s_id
HAVING GROUP_CONCAT(s.c_id) = (SELECT GROUP_CONCAT(s2.c_id) from score s2 where s2.s_id = '01' GROUP BY s2.s_id) 


SELECT DISTINCT s_id
from score
where s_id not in 
(
  -- 课程外的课的学生
	select s_id
	from score
	where c_id not in 
	(
		-- 01所学课程
		SELECT c_id
		from score
		where s_id = '01'
	) 
)	
and s_id != '01'
GROUP BY s_id			-- 再统计学的课程在范围内的学生的课程数量
having COUNT(s_id) = (SELECT COUNT(*) from score where s_id = '01')


-- 13.查询没学过"张三"老师讲授的任一门课程的学生姓名(重点)

-- 同第五题

-- 14. 空

-- 15.查询两门及其以上不及格课程的同学的学号，姓名及其平均成绩（重点）

select s.s_id, avg(s.score)
from score s
GROUP BY s.s_id
HAVING max(s.score) < 60 and count(1) >= 2


-- 16.检索"01"课程分数小于60，按分数降序排列的学生信息（和34题重复，不重点）

select st.*
from score s join student st on s.s_id = st.s_id
where s.c_id = '01' and s.score < 60
ORDER BY s.score desc


-- 17.按平均成绩从高到低显示所有学生的所有课程的成绩以及平均成绩(重重点)【牛啊牛啊】

select s_id, 
max(case when c_id = '01' then score else null end) "01",
max(case when c_id = '02' then score else null end) "02",
max(case when c_id = '03' then score else null end) "03",
avg(score)
from score
GROUP BY s_id
ORDER BY avg(score) desc



-- 18.查询各科成绩最高分、最低分和平均分：以如下形式显示：课程ID，课程name，最高分，最低分，平均分，及格率，中等率，优良率，优秀率

select c.c_id, c.c_name, t.最高分, t.最低分, t.平均分, t.pass, t.middle, t.good, t.great
from course c left join 
(
	SELECT s.c_id, max(s.score) 最高分, min(s.score) 最低分, avg(s.score) 平均分, 
	avg(case when s.score >= 60 then 1 else 0 end) pass, 	
	AVG(case when score >= 70 and score < 80 then 1 else 0 end) as middle,
	AVG(case when score >= 80 and score < 90 then 1 else 0 end) as good,
	AVG(case when score >= 90  then 1 else 0 end) as great
	from score s
	GROUP BY s.c_id
) t on c.c_id = t.c_id



-- 19、按各科成绩进行排序，并显示排名(重点dens_rank(）over（order by 列）

select c_id, s_id, score, DENSE_RANK() over (PARTITION by c_id ORDER BY score desc)
from score 

-- 20、查询学生的总成绩并进行排名（不重点）

select t.s_id, t.sum_score, DENSE_RANK() over (ORDER BY(t.sum_score))
from 
(
	select s_id, sum(score) sum_score
	from score
	GROUP BY s_id
	ORDER BY sum(score) desc
) t


-- 21、查询不同老师所教不同课程平均分从高到低显示(不重点)

select c.c_id, t.t_name, avg(s.score)
from course c left join score s on c.c_id = s.c_id
left join teacher t on c.t_id = t.t_id
group by c.c_id, t.t_name
order by avg(s.score) desc


-- 22、查询所有课程的成绩第2名到第3名的学生信息及该课程成绩（重要 25类似）

select t.c_id, t.s_name, t.score, t.no
from
(
	SELECT s.c_id, st.s_name, s.score, DENSE_RANK() over (PARTITION by s.c_id ORDER BY s.score desc) no
	from score s join student st on s.s_id = st.s_id 
) t
where t.no in (2,3)

-- 23、使用分段[100-85],[85-70],[70-60],[<60]来统计各科成绩，分别统计各分数段人数：课程ID和课程名称(重点和18题类似

select c.c_id, c.c_name, t.pass, t.middle, t.good, t.great
from course c left join 
(
	SELECT s.c_id, 
	sum(case when s.score >= 60 then 1 else 0 end) pass, 	
	sum(case when score >= 70 and score < 80 then 1 else 0 end) as middle,
	sum(case when score >= 80 and score < 90 then 1 else 0 end) as good,
	sum(case when score >= 90  then 1 else 0 end) as great
	from score s
	GROUP BY s.c_id
) t on c.c_id = t.c_id


-- 24、查询学生平均成绩及其名次（同19题，重点）

SELECT t.s_id, t.avg_score, DENSE_RANK() over (order by t.avg_score desc)
from (select s_id, avg(score) avg_score
from score 
GROUP BY s_id) t

-- 25、查询各科成绩前三名的记录（不考虑成绩并列情况）（重点 与22题类似）

select t.c_id, t.s_name, t.score, t.no
from
(
	SELECT s.c_id, st.s_name, s.score, ROW_NUMBER() over (PARTITION by s.c_id ORDER BY s.score desc) no
	from score s join student st on s.s_id = st.s_id 
) t
where t.no <= 3

-- 26、查询每门课程被选修的学生数(不重点)

select c_id, count(*)
from score
GROUP BY c_id

-- 27、 查询出只有两门课程的全部学生的学号和姓名(不重点)

select s.s_id, st.s_name
from score s join student st on s.s_id = st.s_id
GROUP BY s.s_id, st.s_name
having count(*) = 2


-- 28、查询男生、女生人数(不重点)

select s_sex, count(*)
from student
GROUP BY s_sex

-- 29、查询名字中含有"风"字的学生信息（不重点）

select *
from student
where s_name like '%风%'

-- 30、空

-- 31、查询1990年出生的学生名单（重点year）

SELECT *
from student
where year(s_age) = '1990'

-- 32、查询平均成绩大于等于85的所有学生的学号、姓名和平均成绩（不重要）

select s.s_id, st.s_name, avg(score)
from score s join student st on s.s_id = st.s_id
GROUP BY s.s_id, st.s_name
having avg(score) >= 85

-- 33、查询每门课程的平均成绩，结果按平均成绩升序排序，平均成绩相同时，按课程号降序排列（不重要）

select c_id, avg(score)
from score 
GROUP BY c_id
ORDER BY avg(score) asc, c_id desc

-- 34、查询课程名称为"数学"，且分数低于60的学生姓名和分数（不重点）

SELECT st.s_name, s.score
from score s, student st
where s.s_id = st.s_id and s.c_id = (select c_id from course where c_name = '数学') and s.score < 60

-- 35、查询所有学生的课程及分数情况（重点）

select s.s_id, st.s_name, 
sum(case when s.c_id = '01' then s.score else null end )"01",
sum(case when s.c_id = '02' then s.score else null end )"02",
sum(case when s.c_id = '03' then s.score else null end )"03"
from score s join student st on s.s_id = st.s_id
GROUP BY s.s_id, st.s_name

-- 36、查询任何一门课程成绩在70分以上的姓名、课程名称和分数（重点）

select st.s_name, c.c_name, s.score
from score s join student st on s.s_id = st.s_id join course c on c.c_id = s.c_id
where s.score > 70

-- 37、查询不及格的课程并按课程号从大到小排列(不重点)

select *
from score
where score < 60
ORDER BY c_id desc


-- 38、查询课程编号为03且课程成绩在80分以上的学生的学号和姓名（不重要）

SELECT st.s_id, st.s_name
from score s join student st on s.s_id = st.s_id
where s.c_id = '03' and s.score > 80

-- 39、求每门课程的学生人数（不重要）
-- 同26


-- 40、查询选修“张三”老师所授课程的学生中成绩最高的学生姓名及其成绩（重要limit）

select st.s_name, s.score
from score s join student st on s.s_id = st.s_id join course c on s.c_id = c.c_id join teacher t on t.t_id = c.t_id
where t.t_name = '张三' 
ORDER BY s.score desc limit 1

-- 41、查询不同课程成绩相同的学生的学生编号、课程编号、学生成绩 （重点）

select *
FROM score
where s_id in 
(
	select s_id
	FROM score
	GROUP BY s_id
	HAVING min(score) = MAX(score)
)

-- 42、查询每门功成绩最好的前两名（同22和25题）

-- 43、统计每门课程的学生选修人数（超过5人的课程才统计）。要求输出课程号和选修人数，查询结果按人数降序排列，若人数相同，按课程号升序排列（不重要）

SELECT c_id, count(*)
from score
GROUP BY c_id
HAVING COUNT(*) > 5
ORDER BY count(*) desc, c_id asc

-- 44、检索至少选修两门课程的学生学号（不重要）

SELECT s_id, COUNT(*)
FROM score
GROUP BY s_id
HAVING COUNT(*) >= 2

-- 45、查询选修了全部课程的学生信息（重点）

SELECT s_id, COUNT(*)
FROM score
GROUP BY s_id
HAVING COUNT(*) = (SELECT count(*) from course)

-- 47、查询没学过“张三”老师讲授的任一门课程的学生姓名
-- 同第五题

-- 48、查询两门以上不及格课程的同学的学号及其平均成绩


select s_id, avg(score)
from score
where s_id in
(
	SELECT s_id
	FROM score
	where score < 60
	GROUP BY s_id
	HAVING count(*) >= 2 
)
GROUP BY s_id


-- 49、查询各学生的年龄（精确到月份）

SELECT s_id,s_age, floor(DATEDIFF(now(),s_age) / 365)
from student

SELECT s_id,s_age, year(FROM_DAYS(DATEDIFF(now(),s_age)))
from student

-- 50、查询本月过生日的学生

SELECT *
from student
where extract(month from s_age) = extract(month from now())

-- 50.1、查询下月过生日的学生

SELECT *
from student
where extract(month from s_age) = extract(month from now()) + 1
```





## 字符串练习

```sql
-- SQL245 查找字符串中逗号出现的次数
select id, length(string)-length(replace(string, ',', ''))
from strings

-- SQL246 按照first_name最后两个字母升序进行输出
select first_name from employees
order by substring(first_name, length(first_name) - 1, length(first_name))
```

## sql206

```sql
select t1.dept_no, t2.emp_no, t1.salary
from 
    -- 部门、最高薪资
    (select d.dept_no dept_no, max(s.salary) salary
    from salaries s join dept_emp d on d.emp_no = s.emp_no 
    group by dept_no) t1
    join
    -- 部门、员工、薪资
    (select d.dept_no dept_no, d.emp_no emp_no, s.salary salary
    from dept_emp d join salaries s on d.emp_no = s.emp_no) t2
    -- 筛选两者一致的
    on t1.dept_no = t2. dept_no and t1.salary = t2.salary
order by dept_no;
```

## 排序

只有单个
```sql
-- sql195
select * from employees
order by hire_date desc
limit 1;
```

使用子查询，可以处理多个相同的情况
```sql
-- sql195
select * 
from employees
where hire_date = (select max(hire_date) from employees);

-- sql211
select emp_no, salary
from salaries
where salary = (select distinct salary from salaries order by salary desc limit 1,1);
```

### sql212 禁止order by

```sql
-- 方法一
select s.emp_no, s.salary, e.last_name, e.first_name
from salaries s join employees e
on s.emp_no = e.emp_no
where s.salary =              -- 第三步: 将第二高工资作为查询条件
(
    select max(salary) from salaries       -- 第二步: 查出除了原表最高工资以外的最高工资(第二高工资)
    where salary <  ( select max(salary)  from salaries )     -- 第一步: 查出原表最高工资
);



-- 方法二
select s.emp_no, s.salary, e.last_name, e.first_name
from salaries s join employees e on s.emp_no = e.emp_no
where s.salary =
(
    select s1.salary
    from salaries s1 join salaries s2 on s1.salary <= s2.salary     -- 自连接查询
    group by s1.salary                     -- 当s1<=s2链接并以s1.salary分组时一个s1会对应多个s2
    having count(distinct s2.salary) = 2   -- 只要更高工资数量为2，一个是自身，一个是最高，那么对应就是第二高。
);
```

### sql217 t_rank
```sql
--- 1 2 2 3  薪资排序
-- 窗口函数
select emp_no, salary, dense_rank() over (order by salary desc) as t_rank
from salaries
order by salary desc, emp_no asc;


-- 列传给查表 + 聚合函数
SELECT s1.emp_no, s1.salary,
(
    SELECT COUNT(DISTINCT s2.salary) 
    FROM salaries s2 
    WHERE s2.salary >= s1.salary
) AS `rank`  -- 去重：计算并列排名
FROM salaries s1 
ORDER BY s1.salary DESC, s1.emp_no;


-- salary 和 排名， salary作为连接的关键字
select s.emp_no, s.salary, t3.t_rank
from 
(
    select t1.salary, count(t1.salary) t_rank
    from
    (
        select distinct salary
        from salaries
        order by salary desc
    ) t1 
    join
    (
        select distinct salary
        from salaries
        order by salary desc
    ) t2
    on t1.salary <= t2.salary
    group by t1.salary
) t3 join salaries s on t3.salary = s.salary
order by t_rank;
```
发散：
```sql

select emp_no, salary, row_number() over (order by salary desc) as t_rank
from salaries
order by salary desc, emp_no asc;

--- 1 2 3 4  员工排名
select t3.emp_no, s.salary, t3.t_rank
from 
(
    select t1.emp_no emp_no, count(t1.emp_no) t_rank
    from
    (
        select emp_no, salary
        from salaries
        order by salary desc, emp_no asc
    ) t1 
    join
    (
        select emp_no, salary
        from salaries
        order by salary desc, emp_no asc
    ) t2
    on t1.salary < t2.salary or (t1.salary = t2.salary and t1.emp_no >= t2.emp_no) 
    group by t1.emp_no
) t3 join salaries s on t3.emp_no = s.emp_no
order by t_rank;
```

## sql215

```sql
select t1.emp_no, salary_now - salary_past growth
from 
-- 现在工资
(
    select emp_no, salary salary_now
    from salaries
    where to_date='9999-01-01'
) t1 
join 
-- 过去工资
(
    select e.emp_no emp_no, s.salary salary_past
    from employees e join salaries s on e.emp_no = s.emp_no and e.hire_date = s.from_date
) t2 
on t1.emp_no = t2.emp_no 
order by growth;
```

## 同时在线人数

```sql
select date(login_time) login_date, hour(login_time) login_hour, max(online_user_cnt) online_user_cnt_max 
from 
( 
    select user_id, login_time, sum(index1) over(order by login_time)  online_user_cnt 
    from 
    ( 
        select user_id, login_time, 1 as index1 from login_data 
        union all 
        select user_id, exit_time, -1 as index1 from login_data 
    )a
)b 
group by date(login_time) , hour(login_time)
```

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112154691.png)

(1) 把原始数据中的user_id和登录时间(login_time)取出来，然后给一个index，每个用户的登录时都给一个1，然后union all 结束时间，给结束时间(exit_time)时一个-1

(2) 这样我们就能在这个子查询的外层以sum()和开窗函数(over)配合，并且以登录时间升序，这样，每次遇到登录时间的时候就能加1，遇到退出时间就能减1了

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112154692.png)

(3) 我们得到秒级的用户登录量，然后我们求出每小时最大的值，就是以天和小时进行分组，然后取最大值就能得到我们每小时最大在线量了。当然我们也可以直接以天进行分组，得到每天的最大量，因为我们已经求到了秒级的最大量

![alt text](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407112154694.png)

## 合并分数

![image-20240715171936382](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407151719316.png)

```sql
-- 错误
select s.s_id, st.s_name, 
case when s.c_id = '01' then s.score else null end "01",
case when s.c_id = '02' then s.score else null end "02",
case when s.c_id = '03' then s.score else null end "03"
from score s join student st on s.s_id = st.s_id
```

![image-20240715171958743](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407151719825.png)

```sql
select s.s_id, st.s_name, 
sum(case when s.c_id = '01' then s.score else null end )"01",
sum(case when s.c_id = '02' then s.score else null end )"02",
sum(case when s.c_id = '03' then s.score else null end )"03"
from score s join student st on s.s_id = st.s_id
GROUP BY s.s_id, st.s_name
```



![image-20240715172016709](https://cdn.jsdelivr.net/gh/sword4869/pic1@main/images/202407151720790.png)

## 韦恩图

```sql
-- 外连接
select c.name Customers
from customers c left join orders o
on c.id = o.customerId
where o.customerId is null

-- 子查询，更快
select c1.Name Customers 
from Customers c1 
where c1.Id not in (select CustomerId from Orders)		-- 条件就是外连接的连接列
```

