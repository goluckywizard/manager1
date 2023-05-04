package ru.nsu.manager;

import com.roytuts.jaxb.WorkerRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.manager.dto.CrackDTO;
import ru.nsu.manager.dto.StatusDTO;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;

@Service
public class ManagerService {
    @Autowired
    private RabbitTemplate template;
    private final HashMap<UUID, StatusDTO> statuses = new HashMap<>();

    @Value("${queue.name}")
    String queueName;
    @Value("${workers.count}")
    Long workersCount;
    @Value("${workers.alphabetVolume}")
    Long alphabetVolume;

    public UUID sendMessage(CrackDTO message) {
        UUID id = UUID.randomUUID();
        statuses.put(id, new StatusDTO());
        long partCount = (long) Math.pow(alphabetVolume, message.getMaxLength());
        for (int i = 0; i < workersCount; i++) {
            WorkerRequest request = new WorkerRequest();
            request.setHash(message.getHash());
            request.setPartNumber(i);
            request.setPartCount(partCount);
            template.convertAndSend(queueName, request);
        }
        return id;
    }
    public StatusDTO getStatus(String id) {
        UUID requestId = UUID.fromString(id);
        return statuses.getOrDefault(requestId, new StatusDTO("ERROR", null));
    }
}
