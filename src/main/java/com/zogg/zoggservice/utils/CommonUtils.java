package com.zogg.zoggservice.utils;

import com.zogg.zoggservice.dtos.MediaDetails;
import com.zogg.zoggservice.exception.ZoggServiceException;
import java.security.SecureRandom;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtils {

    private static final String OTP = "OTP_";

    public static ZoggServiceException logAndGetException(String errorMessage, Exception e) {
        log.error(errorMessage, e);
        return new ZoggServiceException(errorMessage);
    }

    public static ZoggServiceException logAndGetException(String errorMessage) {
        log.error(errorMessage);
        return new ZoggServiceException(errorMessage);
    }

    public static String getRedisKeyForOtp(String phoneNumber) {
        return OTP + phoneNumber;
    }

    public static Long generateOtp(String phoneNumber) {
        SecureRandom random = new SecureRandom();
        int phoneHash = Math.abs(phoneNumber.hashCode());
        long otp = (phoneHash + random.nextInt(900000)) % 900000 + 100000;
        log.info("Generated OTP for {}: {}", phoneNumber, otp);

        return otp;
    }

    public static List<MediaDetails> updateMediaDetails(
            List<MediaDetails> existingMediaDetails, List<MediaDetails> newMediaDetails) {
        List<String> newDisplayTypes =
                newMediaDetails.stream().map(MediaDetails::getDisplayType).toList();

        existingMediaDetails.removeIf(media -> newDisplayTypes.contains(media.getDisplayType()));
        existingMediaDetails.addAll(newMediaDetails);
        return existingMediaDetails;
    }
}
