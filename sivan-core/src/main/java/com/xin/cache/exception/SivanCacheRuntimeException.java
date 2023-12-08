package com.xin.cache.exception;

/**
 * 缓存运行时异常
 * @author sivan
 *  
 */
public class SivanCacheRuntimeException extends RuntimeException {

    public SivanCacheRuntimeException() {
    }

    public SivanCacheRuntimeException(String message) {
        super(message);
    }

    public SivanCacheRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public SivanCacheRuntimeException(Throwable cause) {
        super(cause);
    }

    public SivanCacheRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
