package com.activeclub.fileserverminio.core.annotation.recoredRequest;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestRecord {
}
