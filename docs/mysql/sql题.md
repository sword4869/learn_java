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