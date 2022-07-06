package org.biot.rule.engine.domain.service;

import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.condition.Condition;
import org.biot.rule.engine.domain.rule.model.condition.ContinuousCondition;
import org.biot.rule.engine.domain.rule.model.condition.CumulativeCondition;
import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPoint;
import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPointRepository;
import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.biot.rule.engine.domain.rule.model.trigger.ReportedPropertyValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
class Judge {
    @Autowired
    private TriggerPointRepository pointRepository;

    /**
     * 检查执行条件是否满足
     *
     * @param model
     * @param pv
     * @return
     */
    boolean checkCondition(RuleModel model, ReportedPropertyValue pv) {
        Condition condition = model.getCondition();
        if (condition instanceof CumulativeCondition) {
            return checkCumulativeCondition((CumulativeCondition) condition, pv);
        } else if (condition instanceof ContinuousCondition) {
            return checkContinuousCondition((ContinuousCondition) condition, pv);
        }

        return false;
    }

    /**
     * 重置持续类型执行条件的状态
     *
     * @param model
     * @param pv
     */
    void resetContinuousState(RuleModel model, ReportedPropertyValue pv) {
        String ruleId = model.getRuleId().getUuid();
        String deviceId = pv.getDeviceId();
        boolean triggered = pointRepository.isLastTriggered(ruleId, deviceId);
        if (triggered) {
            // write un-triggered point
            TriggerPoint point = build(model.getRuleId(), pv, false);
            pointRepository.save(point);
        }
        Condition condition = model.getCondition();
        pointRepository.markTriggeredState(ruleId, deviceId, false, condition.getPeriod());
    }

    /**
     * 检查累计类型执行条件是否满足
     *
     * @param condition
     * @param pv
     * @return
     */
    private boolean checkCumulativeCondition(CumulativeCondition condition, ReportedPropertyValue pv) {
        /**
         * 1. 存入trigger point
         * 2. 获取周期内的trigger point list，判断是否满足条件
         */
        RuleId ruleId = condition.getRuleId();
        TriggerPoint point = build(ruleId, pv, true);
        pointRepository.save(point);
        long endTime = pv.getEventTime();
        long fromTime = endTime - condition.getPeriod() * TimeUnit.SECONDS.toMillis(1);
        // endTime加1毫秒，处理边界的问题
        List<TriggerPoint> list = pointRepository.query(ruleId, pv.getDeviceId(), fromTime, endTime + 1);
        return list.size() >= condition.getTimes();
    }

    /**
     * 检查持续类型执行条件是否满足
     *
     * @param condition
     * @param pv
     * @return
     */
    private boolean checkContinuousCondition(ContinuousCondition condition, ReportedPropertyValue pv) {
        /**
         * 1. 获取原先continuous state
         * 2. 更新continuous true，并设置expire time
         * 3. 存入trigger point
         * 4. 如果原先continuous state为false，返回false
         * 5. 如果原先continuous state为true，获取特定时间段内的trigger point，判断是否满足执行条件
         */

        return false;
    }

    /**
     * 构建trigger point
     *
     * @param ruleId
     * @param pv
     * @param triggered
     * @return
     */
    private TriggerPoint build(RuleId ruleId, ReportedPropertyValue pv, boolean triggered) {
        return TriggerPoint.builder().triggered(triggered).ruleId(ruleId).sourceId(pv.getDeviceId())
                .eventTime(pv.getEventTime()).desc(descOf(pv)).build();
    }

    /**
     * 生成上报属性值的描述
     *
     * @param pv
     * @return
     */
    private String descOf(ReportedPropertyValue pv) {
        return pv.getDeviceId() + "##" + pv.getPropertyId() + ": " + pv.getPropertyValue();
    }
}
