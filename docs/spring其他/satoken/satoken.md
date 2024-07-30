```java
@RestController
@SaCheckLogin
public class EnvironmentalInfoController extends AbstractActionController {
    @GetMapping(ENVIRONMENTAL_INFO_LIST)
    @SaCheckPermission("environmental:list")
    public Rest list(EnvironmentalInfoCriteria criteria) {
        ...
    }
```

`@SaCheckLogin`

`@SaCheckPermission("environmental:list")`