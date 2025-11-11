package william.personal.WOWBackend.services;

import org.springframework.stereotype.Service;
import william.personal.WOWBackend.models.entity.Log;
import william.personal.WOWBackend.repositories.LogRepository;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public boolean createLog(String username, String message, String type) {
        Log log = Log.builder().username(username)
                .message(message).type(type).build();

        this.logRepository.save(log);
        return true;
    }
}
