package com.sentana.job.response;

import java.util.UUID;

public class ResourceNotFound {
    public static String getLongId(Long id) {
        return  id + " not found";
    }
    public static String getUUId(UUID id){
        return  id + " not found";
    }
}
