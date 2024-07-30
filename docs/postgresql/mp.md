## insert

```xml
<insert id="submit">
    <selectKey keyProperty="id" resultType="long" order="BEFORE">
        select nextval('eval_id_seq')
    </selectKey>
    insert into eval_safety_info(id,enterprise_id,is_create_safety,is_danger_control,is_get_safety_admin,is_get_safety_management,
    is_dead_happen,is_injured_happen,is_get_health_certificate,is_completed_regular,
    is_completed_health,is_completed_declaration,is_disease_case,
    punishment_count,nation_complaint_count,pro_complaint_count,sz_complaint_count,tc_complaint_count,score,
        created_by,updated_by,delete_flag, zj_complaint_count ,is_dead_in_5 ,dead_rate ) values
    (#{id},#{enterpriseId},#{isCreateSafety},#{isDangerControl},#{isGetSafetyAdmin},#{isGetSafetyManagement},
    #{isDeadHappen},#{isInjuredHappen},#{isGetHealthCertificate},#{isCompletedRegular},
    #{isCompletedHealth},#{isCompletedDeclaration},#{isDiseaseCase},
    #{punishmentCount},#{nationComplaintCount},#{proComplaintCount},#{szComplaintCount},#{tcComplaintCount},#{score},
    #{createdUserId}, #{updatedUserId},'0',#{zjComplaintCount},#{deadInfo},#{deadInfoRate})
</insert>
```

```xml
<selectKey keyProperty="id" resultType="long" order="BEFORE">
    select nextval('eval_id_seq')
</selectKey>
```

