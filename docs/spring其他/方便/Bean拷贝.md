```java
import org.springframework.beans.BeanUtils;

RoomInfoVO roomInfo = new RoomInfoVO('big house', 112, True);
final RoomInfoEntity roomInfoEntity = new RoomInfoEntity();
BeanUtils.copyProperties(roomInfo, roomInfoEntity);
```

那么和hutools的有什么区别？