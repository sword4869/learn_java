
## update

都要这样写，因为前端传来的可能是全部的字段，也可能是只传更新的字段（其他未变动的字段就是null）。

```xml
<update id="update">
    update emp
    <set>
        update_time = now(), 
        updated_user_id = #{updatedUserId}		# 固定传、固定设置的东西就写前面
        <if test="username != null and username != ''">			# 不仅要 username != null, 还要 username != ''
            username=#{username},
        </if>
        <if test="gender != null and gender != ''">
            gender=#{gender}
    </set>
    where id=#{id}
</update>
```
