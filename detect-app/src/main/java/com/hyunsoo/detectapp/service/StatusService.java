package com.hyunsoo.detectapp.service;

import com.hyunsoo.detectapp.beans.Status;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface StatusService {

    boolean updateStatus(Status status);

    Status getStatusByCustId(long custId);

    List<Status> getStatusByUUID(long uuid);
}
