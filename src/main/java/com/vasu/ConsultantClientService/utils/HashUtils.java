package com.vasu.ConsultantClientService.utils;

import org.hashids.Hashids;

public class HashUtils {
    public static String encode(Long input)  {
        Hashids hashids = new Hashids("this is my project", 10);
        return hashids.encode(input);
    }

    public static Long decode(String input) {
        Hashids hashids = new Hashids("this is my project", 10);
        long[] numbers = hashids.decode(input);
        if (numbers.length > 0) return numbers[0];
        return null;
    }

    public static Long getId(String hashId) {
        Long myId = null;
        if (hashId != null && !hashId.isEmpty()) myId = HashUtils.decode(hashId);
        return myId;
    }
}
