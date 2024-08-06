后两种方式适合表中有大量数据，一次全部返回给前端太大。

## 方式1：一次性

pagehelper返回给前端所有页的数据，由前端自己决定显示局部显示。

## 方式2：paghelper

前端传 limit每页大小 和 page第几页（对应pagehelper参数） ＋ pagehelper获取数据

返回给前端就是那一页的。

## 方式3：手写limit

（1）不用pagehelper，手写sql limit。

要么前端算 start，要么后端算 start：

- limit每页大小，page第几页。那么由后端来算 `start = (page - 1) * limit`

- limit每页大小，start跳过多少记录。那么由前端自己算 `start`，后端省得计算。

（2）先查所有数据的总条数，再查某一页



QA：`queryTotal` 是统计总记录数，还是那一页的记录数？

​	答案是总记录数。

​	~~因为是为了快，不排序、不limit，如果是后者，那么sql都查出来了，还统计数量，直接返回就行。~~

​	是为了“分页”需求，需要返回给前端所有记录的总数量。



QA：担心mapper没有查到数据？

​	它返回的不是null，就是我们想要的空List。

​	除非是特别老版本，会返回null。



QA：能去掉`queryTotal`，直接`return teacherInfoMapper.queryVOPage(teacherInfoCriteria);`吗？

​	如果仅是查询记录 List，那么可以。

​	但这里还是为了“分页”需求，需要返回给前端所有记录的总数量。



QA：`total > 0 && condition.getStart() < total`这种优化。

​	主要是为了总量为0，相当于只交互一次数据库，不加判断，就是每次都调两次数据库。

​	对于超出的情况，可以，但没必要。因为一般第一次展示的时候，就会返回 total，前端知道了就不会展示

### 方式1：前端传 limit page，后端负责计算 start

```java
// controller
/**
 * 查询房间信息分页
 */
@PostMapping(WebURIMappingConstant.PAGE)
public Rest<JsonPagedVO<RoomInfoVO>> queryRoomInfoPage(@RequestBody @Validated RoomInfoCondition condition) {
    LOGGER.info("用户：{}，查询房间信息分页:{}", getCurrentUserId(), condition);

    return RestBody.okData(roomInfoService.queryRoomInfoPage(condition));
}
```



```java
// service
@Override
public JsonPagedVO<RoomInfoVO> queryRoomInfoPage(RoomInfoCondition condition) {
    LOGGER.info("查询房间信息分页，condition:{}", condition);
    // 查数量
    final long total = roomInfoRepository.queryRoomInfoTotal(condition);
    // 查具体信息
    final List<RoomInfoVO> list = total > 0 ? roomInfoRepository.queryRoomInfoPage(condition) : Collections.emptyList();
    // 模仿Page格式的类
    return new JsonPagedVO<>(list, total);
}


public final class JsonPagedVO<T> {
    private Long total;
    private List<T> rows;
    ...
}
```

```xml
<!--查询房间信息总数-->
<select id="queryRoomInfoTotal" resultType="java.lang.Long">
    SELECT COUNT(*)
    FROM t_room_info
    WHERE deleted_flag = '0'
    <if test="roomName != null and roomName != ''">
        AND room_name LIKE '%'||#{roomName}||'%'
    </if>
</select>

<!--查询房间信息分页-->
<select id="queryRoomInfoPage" resultType="com.safesoft.domain.appointment.equipment.room.vo.RoomInfoVO">
    SELECT id,
           room_name,
           room_description
        FROM t_room_info
    WHERE deleted_flag = '0'
    <if test="roomName != null and roomName != ''">
        AND room_name LIKE '%'||#{roomName}||'%'
    </if>
    ORDER BY created_time DESC
    LIMIT #{limit} OFFSET #{start}
</select>
```

limit和start

```java
package com.safesoft.domains;

@Date
public abstract class AbstractPaginationQueryCondition extends AbstractBaseCondition {
    // 起始条数
    private Integer start = 0;
    // 每页个数
    private Integer limit = 20;
    // 页数
    private Integer page = 1;

    public AbstractPaginationQueryCondition() {
    }

    public String toString() {
        String var10000 = super.toString();
        return "AbstractPaginationQueryCondition(super=" + var10000 + ", start=" + this.getStart() + ", limit=" + this.getLimit() + ", page=" + this.getPage() + ")";
    }
}
```

```java
package com.safesoft.domain.institution.criteria;

import com.safesoft.domains.AbstractPaginationQueryCondition;
import lombok.Data;

@Data
public class TeacherInfoCriteria extends AbstractPaginationQueryCondition {
    private Long institutionId;

    @Override		// @Override是AbstractPaginationQueryCondition中有getStart(), （与@Data无关，@Data见我们写了就不会自己生成了）。
    public Integer getStart() {		/* 目的是自动计算 start = (page - 1)*limit  */
        final Integer page = getPage();	// 第几页
        final Integer limit = getLimit(); // 每页的条数
        if (page != null && limit != null) {
            return (page - 1) * limit;
        }
        return null;
    }
}
```

### 方式2：前端传 limit start, 前端负责计算 start

只要去掉 `getStart()`的重写就行。

同时，也用不上 `page` 属性了。

```java
package com.safesoft.domain.institution.criteria;

import com.safesoft.domains.AbstractPaginationQueryCondition;
import lombok.Data;

@Data
public class TeacherInfoCriteria extends AbstractPaginationQueryCondition {
    private Long institutionId;
}
```

### 多表联查+一对多+分页

```sql
select ...				// 一对多，结果要放到resultmap中
from xxx join xxx
limit 10 			
```

此时的limit是错误的。因为我们想要限制的分页数量，是一对多的结果，而不是一对多的原始数据（比如，15条转10条）。

所以，一个sql解决不了。

这种就两次访问数据库，第一次查表不join查一对多，就可以用limit 10，然后放到resultmap，第二次查表查对应多的，结果用java的set到第一个结果中。
