package com.hyunsoo.detectapp.aop;

import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class DetectAspect {

    private final StatusRepository statusRepository;

    @Around(value = "@annotation(com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN)")
    boolean around(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        int idx = args.length - 1;
        DataBox dataBox = (DataBox) args[idx];
        long uuid = dataBox.getUuid();
        log.info("같은 디바이스에서 로그인 했고, 30분이내에 대출을 시도하는지 검사");
        log.info("디바이스 아이디에서,");
        List<Status> statusList = statusRepository.findAllByUuidOrderByCreatedDateAsc(uuid);
        if(!CollectionUtils.isEmpty(statusList) && statusList.size() >= 2){
            //TODO DETECT DB에 INSERT -> 의심 포인트로
            Status status = getDetectedStatusByCustId(statusList, dataBox.getCustId());
            if(!Objects.isNull(status)){
                LocalDateTime visitedTime = status.getCreatedDate();
                LocalDateTime now = dataBox.getCreatedDate();
                if(visitedTime.isAfter(now.minusMinutes(30))){
                    log.info("의심되는 트랜잭션을 발견했습니다.");
                    log.info("현재 디바이스 고유 아이디 : " + dataBox.getUuid());
                    log.info("같은 디바이스에 로그인 한 유저 수 : " + statusList.size());
                    log.info("같은 디바이스에 로그인 한 유저 아이디 : ");
                    for(Status tmpStatus : statusList){
                        log.info(tmpStatus.getCustId() + " ");
                    }
                    log.info("로그인 시간 : " + visitedTime.toString());
                    log.info("대출 시도 시간 : " + now.toString());
                }
                return false;
            }
        }
        return (Boolean)pjp.proceed();
    }

    private static Status getDetectedStatusByCustId(List<Status> statusList, long custId){
        for(Status status : statusList){
            if(status.getCustId() == custId){
                return status;
            }
        }
        return null;
    }

}
