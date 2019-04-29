package io.moia.router.proto

import com.google.common.net.MediaType
import com.google.protobuf.GeneratedMessageV3
import io.moia.router.SerializationHandler
import java.util.Base64

class ProtoSerializationHandler : SerializationHandler {

    private val json = MediaType.parse("application/json")

    override fun supports(acceptHeader: MediaType, body: Any): Boolean =
        body is GeneratedMessageV3

    override fun serialize(acceptHeader: MediaType, body: Any): String {
        val message = body as GeneratedMessageV3
        println("$acceptHeader $message")
        return if (acceptHeader.`is`(json)) {
            ProtoBufUtils.toJsonWithoutWrappers(message)
        } else {
            Base64.getEncoder().encodeToString(message.toByteArray())
        }
    }
}