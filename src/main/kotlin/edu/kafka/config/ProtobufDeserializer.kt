package edu.kafka.config

import protobuf.MessageProto.MyMessage
import org.apache.kafka.common.serialization.Deserializer

class ProtobufDeserializer : Deserializer<MyMessage> {
    override fun deserialize(topic: String?, data: ByteArray?): MyMessage {
        return data?.let { MyMessage.parseFrom(it) } ?: MyMessage.getDefaultInstance()
    }
}
