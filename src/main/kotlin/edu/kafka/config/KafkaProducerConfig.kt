package edu.kafka.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import protobuf.MessageProto.MyMessage

@Configuration
class KafkaProducerConfig(
    private val kafkaProperties: KafkaProperties
) {

    @Bean
    fun producerFactory(): ProducerFactory<String, MyMessage> {
        val configProps = kafkaProperties.buildProducerProperties()
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, MyMessage> {
        return KafkaTemplate(producerFactory())
    }
}
