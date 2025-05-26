package edu.kafka.config

import protobuf.MessageProto.MyMessage
import org.apache.kafka.common.serialization.Serializer
import java.io.ByteArrayOutputStream

class ProtobufSerializer : Serializer<MyMessage> {
    override fun serialize(topic: String?, data: MyMessage?): ByteArray {
        return data?.toByteArray() ?: ByteArray(0)
    }
}
