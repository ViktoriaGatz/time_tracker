package ru.example.been;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.example.entity.task.Task;
import ru.example.entity.user.User;
import ru.example.repository.TaskRepository;
import ru.example.service.TaskService;
import ru.example.service.TaskServiceImpl;

import java.util.Date;
import java.util.Objects;

@EnableScheduling
@Configuration
public class AppContext {

    private static final Logger log = Logger.getLogger(AppContext.class.getName());

    private TaskServiceImpl taskServiceImpl;

    @Autowired
    public AppContext(TaskServiceImpl taskServiceimpl) {
        this.taskServiceImpl = taskServiceimpl;
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
    // @Scheduled(cron = "0 0 17 * * SUN")
    // @Scheduled(cron = "*/30 * * * * *") // каждые 30 секунд
    // @Scheduled(fixedRate = 5000)
    @Scheduled(fixedDelay = 60000L)
    public void reportCurrentTime() {
        try {
            taskServiceImpl.delete_task_info_TIME_LIMIT();
            log.info("Delete data. The time is now " + new Date());
        } catch (NullPointerException npe) {
            log.warn("Kek " + npe);
        }
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