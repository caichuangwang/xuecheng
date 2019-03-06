package com.xuecheng.framework.domain.course.ext;

import com.xuecheng.framework.domain.course.TeachPlan;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by admin on 2018/2/7.
 * @author 蔡闯王
 */
@Data
@ToString
public class TeachPlanNode extends TeachPlan {

    List<TeachPlanNode> children;

}
