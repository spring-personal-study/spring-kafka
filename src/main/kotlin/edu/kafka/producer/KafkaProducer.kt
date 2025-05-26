package edu.kafka.producer

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import protobuf.MessageProto.MyMessage

@Service
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, MyMessage>
) {
    fun sendMessage(topic: String, key: String, message: MyMessage) {
        kafkaTemplate.send(topic, key, message)
    }
}
