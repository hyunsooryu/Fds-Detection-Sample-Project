package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.aop.target.SAME_UUID_LOAN;
import com.hyunsoo.detectapp.beans.CustomerStatus;
import com.hyunsoo.detectapp.beans.Status;
import com.hyunsoo.detectapp.repository.cache.CustomerCacheRepository;
import com.hyunsoo.detectapp.repository.db.StatusRepository;
import com.hyunsoo.detectapp.utils.StatusUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    private final CustomerCacheRepository customerCacheRepository;

    @Override
    public boolean updateStatus(Status status) {
        boolean updatedResult = false;
        try {
            customerCacheRepository.save(StatusUtil.convertToCustomerStatus(status));
            statusRepository.save(status);
            updatedResult = true;
        }catch (Exception e){
            log.error("고객 상태정보 업데이트 에러", e);
        }
        return updatedResult;
    }

    @Override
    public Status getStatusByCustId(long custId) {

        Status status = null;
        //cache
        Optional<CustomerStatus> customerStatus = customerCacheRepository.findById(custId);
        if(customerStatus.isEmpty()){
            log.info("get statusByCustId : cache miss");
            log.info("go to DB");
            status = statusRepository.findById(custId).orElse(null);
            if(status != null){
                log.info("save cache, {}", status.toString());
                customerCacheRepository.save(StatusUtil.convertToCustomerStatus(status));
            }
        }else{
            log.info("get statusByCustId : cache hit");
           status = StatusUtil.convertToStatus(customerStatus.get());
        }

        return status;
    }

    @Override
    public List<Status> getStatusByUUID(long uuid) {
        List<Status> statusList = statusRepository.findAllByUuidOrderByCreatedDateAsc(uuid);
        return statusList;
    }


}
