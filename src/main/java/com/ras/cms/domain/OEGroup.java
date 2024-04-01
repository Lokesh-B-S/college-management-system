//group - open elective groups + PE also, for openelectivegroupallotment to students

package com.ras.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class OEGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long groupId;

    private Long groupNo;

    public OEGroup(){}

    //constructor needed for initApplicationService
    public OEGroup(Long groupNo){this.groupNo = groupNo; }
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(Long groupNo) {
        this.groupNo = groupNo;
    }
}
