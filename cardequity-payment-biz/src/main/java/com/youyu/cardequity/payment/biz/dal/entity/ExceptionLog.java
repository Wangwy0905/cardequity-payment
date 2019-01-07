package com.youyu.cardequity.payment.biz.dal.entity;

import com.youyu.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import static com.youyu.cardequity.common.base.util.UuidUtil.uuid4NoRail;
import static org.apache.commons.lang3.StringUtils.substring;

/**
 * @author panqingqing
 * @version v1.0
 * @date 2018年12月6日 下午10:00:00
 * @work 异常日志信息表
 */
@Getter
@Setter
@Table(name = "TB_EXCEPTION_LOG")
public class ExceptionLog extends BaseEntity<String> {

    /**
     * 主键
     */
    @Id
    @Column(name = "ID")
    private String id;

    /**
     * 方法名
     */
    @Column(name = "METHOD_NAME")
    private String methodName;

    /**
     * 请求参数
     */
    @Column(name = "REQUEST_PARAMETER")
    private String requestParameter;

    /**
     * 异常编号:可以在对应的日志elk上面查找
     */
    @Column(name = "EXCEPTION_ID")
    private String exceptionId;

    /**
     * 异常码
     */
    @Column(name = "EXCEPTION_CODE")
    private String exceptionCode;

    /**
     * 异常信息
     */
    @Column(name = "EXCEPTION_MSG")
    private String exceptionMsg;

    /**
     * 类型:1:业务异常;2:非业务异常
     */
    @Column(name = "TYPE")
    private String type;

    public ExceptionLog() {
        this.id = uuid4NoRail();
    }

    public ExceptionLog(String exceptionId, String methodName, String requestParameter, String exceptionMsg, String type) {
        this();
        this.exceptionId = exceptionId;
        this.methodName = methodName;
        this.requestParameter = substring(requestParameter, 0, 2000);
        this.exceptionMsg = substring(exceptionMsg, 0, 1000);
        this.type = type;
    }

    public ExceptionLog(String exceptionId, String methodName, String parameter, String code, String desc, String type) {
        this(exceptionId, methodName, parameter, desc, type);
        this.exceptionCode = code;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
