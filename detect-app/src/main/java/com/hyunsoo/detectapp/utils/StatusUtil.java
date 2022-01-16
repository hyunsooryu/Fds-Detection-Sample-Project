package com.hyunsoo.detectapp.utils;

import com.hyunsoo.detectapp.beans.CustomerStatus;
import com.hyunsoo.detectapp.beans.DataBox;
import com.hyunsoo.detectapp.beans.Status;

public class StatusUtil {

    public static CustomerStatus convertToCustomerStatus(Status status){
        CustomerStatus customerStatus = new CustomerStatus();
        customerStatus.setUUID(status.getUuid());
        customerStatus.setCustId(status.getCustId());
        customerStatus.setVpnUseYn(status.getVpnUseYn());
        customerStatus.setCreatedDate(status.getCreatedDate());
        customerStatus.setLoginYn(status.getLoginYn());
        return customerStatus;
    }

    public static Status convertToStatus(CustomerStatus customerStatus){
        Status status = new Status();
        status.setCreatedDate(customerStatus.getCreatedDate());
        status.setCustId(customerStatus.getCustId());
        status.setVpnUseYn(customerStatus.getVpnUseYn());
        status.setUuid(customerStatus.getUUID());
        status.setLoginYn(customerStatus.getLoginYn());
        return status;
    }

    public static Status convertToStatus(DataBox dataBox){
        Status status = new Status();
        status.setCreatedDate(dataBox.getCreatedDate());
        status.setUuid(dataBox.getUuid());
        status.setVpnUseYn(dataBox.getVpnUserYn());
        status.setCustId(dataBox.getCustId());
        return status;
    }
}
