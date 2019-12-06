package org.enterprisedlt.general.codecs

/**
 * @author Alexey Polubelov
 */
trait BinaryCodec {

    def encode[T](value: T): Array[Byte]

    def decode[T](value: Array[Byte], clz: Class[T]): T
}
