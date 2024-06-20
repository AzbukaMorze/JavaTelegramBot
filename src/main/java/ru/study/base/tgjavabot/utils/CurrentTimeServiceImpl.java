package ru.study.base.tgjavabot.utils;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CurrentTimeServiceImpl implements CurrentTimeService {

    @Override
    public Date getCurrentTime() {
        return new Date();
    }
}
