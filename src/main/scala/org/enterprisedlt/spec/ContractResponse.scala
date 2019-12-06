package org.enterprisedlt.spec

/**
 * @author Maxim Fedin
 */
sealed trait ContractResponse[+E, +V] {

    def get: V

    def toEither: Either[E, V]
}

trait QueryResult[+E, +V] extends ContractResponse[E, V] {
}

trait InvokeResult[+E, +V] extends ContractResponse[E, V] {
    def commit(): Unit
}
