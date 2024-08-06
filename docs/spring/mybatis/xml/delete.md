## delete

```xml
<!-- delete from emp where id in (1,2,3); -->
<delete id="deleteByIds">
    delete from emp where id in
    <foreach collection="list" item="id" separator="," open="(" close=")">
        #{id}
    </foreach>
</delete>
```