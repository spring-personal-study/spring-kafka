package edu.kafka.consumer

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service
import protobuf.MessageProto.MyMessage
import edu.kafka.producer.KafkaProducer

@Service
class KafkaConsumer(
    private val kafkaProducer: KafkaProducer
) {

    @KafkaListener(
        topics = ["remittance-1"],
        groupId = "remittance-group-1",
        properties = [
            "key.deserializer=org.apache.kafka.common.serialization.StringDeserializer",
            "value.deserializer=edu.kafka.config.ProtobufDeserializer"
        ]
    )
    fun consume(record: ConsumerRecord<String, MyMessage>) {
        val message = record.value()
        println("====== Protobuf 메시지 수신 ======")
        println("id: ${message.id}")
        println("content: ${message.content}")
        println("==================================")

        kafkaProducer.sendMessage("remittance-2", record.key(), message)
    }
}
