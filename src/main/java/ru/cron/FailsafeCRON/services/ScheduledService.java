package ru.cron.FailsafeCRON.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.cron.FailsafeCRON.entities.ImportantData;
import ru.cron.FailsafeCRON.repositories.ImportantDataRepository;

import java.time.Duration;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class ScheduledService {
    private final ImportantDataRepository repository;
    private final StringRedisTemplate redisTemplate;

    @Scheduled(fixedRate = 60000)
    public void script() {
        Object lock = redisTemplate.opsForValue().get("lock");
        if (lock == null) {
            redisTemplate.opsForValue().set("lock", "lock", Duration.ofSeconds(10));
            ImportantData data = new ImportantData();
            data.setTime(LocalTime.now());
            ImportantData saved = repository.save(data);
            System.out.println("Im lock this shit");
        } else {
            System.out.println("Im slave now");
        }
    }
}
