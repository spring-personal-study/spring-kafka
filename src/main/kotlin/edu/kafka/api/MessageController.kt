package edu.kafka.api

import edu.kafka.dto.MyMessageRequest
import edu.kafka.producer.KafkaProducer
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import protobuf.MessageProto.MyMessage

@RestController
@RequestMapping("/api/message")
class MessageController(
    private val kafkaProducer: KafkaProducer,
) {

    @PostMapping("/send")
    fun sendMessage(@RequestBody request: MyMessageRequest): String {
        val message = MyMessage.newBuilder()
            .setId(request.id)
            .setContent(request.content)
            .build()

        kafkaProducer.sendMessage(
            topic = "remittance-1",
            key = request.id,
            message = message
        )

        return "메시지가 Kafka에 전송되었습니다."
    }
}
