package com.hyunsoo.detectapp.aop;

import com.hyunsoo.detectapp.aop.target.VPN_USER_TRANSFER;
import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.DetectPoint;
import com.hyunsoo.detectapp.beans.DetectPoints;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.repository.db.DetectRepository;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import com.hyunsoo.detectapp.service.ScenarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Aspect
@Slf4j
public class DetectAspect {

    private final StatusRepository statusRepository;

    private final DetectRepository detectRepository;

    private final ScenarioService scenarioService;

    @Around(value = "@annotation(com.hyunsoo.detectapp.aop.target.VPN_USER_TRANSFER)")
    boolean detectVpnUserTransferInSpecificMin(ProceedingJoinPoint pjp) throws Throwable{
        //해당 시나리오가 유효 할 때만, 검사 진행
        if(scenarioService.isValidScenarioNow(DetectPoints.VPN_USER_TRANSFER.getCode())) {
            DataBox dataBox = getDataBoxFromArgs(pjp);
            log.info("VPN 접속 후 10분 이내에 이체를 시도하는 지 검사");
            long custId = dataBox.getCustId();
            Status status = statusRepository.findById(custId).orElse(null);
            if (Objects.isNull(status)) {
                log.info("로그인 상태 값이 없기 때문에 이체 자체가 불가합니다.");
                return false;
            } else {
                LocalDateTime loginTime = status.getCreatedDate();
                LocalDateTime now = dataBox.getCreatedDate();
                boolean isVpnUser = "Y".equals(dataBox.getVpnUserYn());
                //VPN_USER 이면서 10분안에 대출 트랜잭션 시도를 한다면
                if (isVpnUser && loginTime.isAfter(now.minusMinutes(10))) {
                    log.info("의심되는 트랜잭션을 발견했습니다.");
                    log.info("현재 디바이스 고유 아이디 : " + dataBox.getUuid());
                    log.info("현재 고객 아이디 : " + dataBox.getCustId());
                    log.info("vpn으로 로그인 시간 : " + loginTime.toString());
                    log.info("이체 시도 시간 : " + now.toString());
                    detectInsert(dataBox, DetectPoints.VPN_USER_TRANSFER);
                    return false;
                }
            }
        }else{
            log.info("현대 해당 시나리오(탐지코드) : {} 의 설정이 꺼져있습니다.", DetectPoints.VPN_USER_TRANSFER.getCode());
        }
        return (Boolean)pjp.proceed();
    }

    @Around(value = "@annotation(com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN)")
    boolean detectSameUUIDLoan(ProceedingJoinPoint pjp) throws Throwable {
        //해당 시나리오가 유효할 때만 검사진행
        if(scenarioService.isValidScenarioNow(DetectPoints.SAME_UUID_LOAN.getCode())) {
            DataBox dataBox = getDataBoxFromArgs(pjp);
            long uuid = dataBox.getUuid();
            log.info("같은 디바이스에서 로그인 했고, 30분이내에 대출을 시도하는지 검사");
            log.info("디바이스 아이디에서,");
            List<Status> statusList = statusRepository.findAllByUuidOrderByCreatedDateAsc(uuid);
            if (!CollectionUtils.isEmpty(statusList) && statusList.size() >= 2) {
                //TODO DETECT DB에 INSERT -> 의심 포인트로
                Status status = getDetectedStatusByCustId(statusList, dataBox.getCustId());
                if (!Objects.isNull(status)) {
                    LocalDateTime visitedTime = status.getCreatedDate();
                    LocalDateTime now = dataBox.getCreatedDate();
                    if (visitedTime.isAfter(now.minusMinutes(30))) {
                        log.info("의심되는 트랜잭션을 발견했습니다.");
                        log.info("현재 디바이스 고유 아이디 : " + dataBox.getUuid());
                        log.info("같은 디바이스에 로그인 한 유저 수 : " + statusList.size());
                        log.info("같은 디바이스에 로그인 한 유저 아이디 : ");
                        for (Status tmpStatus : statusList) {
                            log.info(tmpStatus.getCustId() + " ");
                        }
                        log.info("로그인 시간 : " + visitedTime.toString());
                        log.info("대출 시도 시간 : " + now.toString());
                        detectInsert(dataBox, DetectPoints.SAME_UUID_LOAN);
                    }
                    return false;
                }
            }
        }else{
            log.info("현대 해당 시나리오(탐지코드) : {} 의 설정이 꺼져있습니다.", DetectPoints.SAME_UUID_LOAN.getCode());
        }
        return (Boolean)pjp.proceed();
    }

    private static DataBox getDataBoxFromArgs(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        int idx = args.length - 1;
        DataBox dataBox = (DataBox) args[idx];
        return dataBox;
    }


    private static Status getDetectedStatusByCustId(List<Status> statusList, long custId){
        for(Status status : statusList){
            if(status.getCustId() == custId){
                return status;
            }
        }
        return null;
    }

    private void detectInsert(DataBox dataBox, DetectPoints detectPoints){
        DetectPoint detectPoint = new DetectPoint();
        detectPoint.setDetectPoint(detectPoints.getVal());
        detectPoint.setDetectPointCd(detectPoints.getCode());
        detectPoint.setCustId(dataBox.getCustId());
        detectPoint.setCreatedDate(dataBox.getCreatedDate());
        try {
            detectRepository.save(detectPoint);
        }catch (Exception e){
            log.error("탐지 데이터 DB INSERT 에러", e);
        }
    }
}
