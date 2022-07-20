package com.devit.board.rabbitMQ;

import com.devit.board.rabbitMQ.dto.ResumeDto;
import com.devit.board.rabbitMQ.service.ResumeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RabbitMqReceiver implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitMqReceiver.class);

    private final ResumeService resumeService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }
    // 소비할 큐를 지정

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receivedMessage(ResumeDto resumedto) {
        logger.info("User Details Received is.. " + resumedto);
        resumeService.saveResume(resumedto);

    }
}