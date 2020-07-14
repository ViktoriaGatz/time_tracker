package ru.example.been;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import ru.example.repository.TaskRepository;
import ru.example.service.TaskServiceImpl;
import java.util.Date;

@EnableScheduling
@Configuration
public class AppContext {

    private static final Logger log = Logger.getLogger(AppContext.class.getName());

    private TaskRepository taskRepository;

    @Bean
    public TaskServiceImpl taskServiceImpl() {
        return new TaskServiceImpl(taskRepository);
    }

    // private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    // log.info("The time is now " + dateFormat.format(new Date()));

    /*
        Example patterns:

        "0 0 * * * *" = the top of every hour of every day.
        "звёздочка слеш 10 * * * * *" = every ten seconds.
        "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
        "0 0 6,19 * * *" = 6:00 AM and 7:00 PM every day.
        "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.
        "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
        "0 0 0 25 12 ?" = every Christmas Day at midnight
     */
    // @Scheduled(fixedRate = 5000)
    // @Scheduled(fixedDelay = 5000L)
    // @Scheduled(cron = "0 0 17 * * SUN")
    @Scheduled(cron = "*/30 * * * * *") // каждые 30 секунд
    public void reportCurrentTime() {
        taskServiceImpl().delete_task_info_TIME_LIMIT();
        log.info("Delete data. The time is now " + new Date());
    }
    /*
        @Bean
        @Lazy
        @Scope("prototype")
        public User userBean() {
            log.info("Been User create");
            return new User();
        }

        // @EnableTransactionManagement
    */
}