package org.enterprisedlt.spec

import scala.util.Try

/**
 * @author Alexey Polubelov
 */

sealed trait ContractResult[E, V]

sealed trait Fail[E, V] extends ContractResult[E, V]

case class ExecutionError[E, V](msg: String) extends Fail[E, V]

case class ErrorResult[E, V](payload: E) extends Fail[E, V]

case class Success[E, V](payload: V) extends ContractResult[E, V]

object ContractResultConversions {

    implicit def Result2Either[E, V]: ContractResult[E, V] => Either[Fail[E, V], V] = {
        case er: ErrorResult[E, V] => Left(er)
        case ee: ExecutionError[E, V] => Left(ee)
        case Success(v) => Right(v)
    }

    implicit def Either2Result[E, V]: Either[E, V] => ContractResult[E, V] = {
        case Right(()) => Success(null.asInstanceOf[V])
        case Right(x) => Success(x)
        case Left(e) => ErrorResult(e)
    }

    implicit def Try2Result[V]: Try[V] => ContractResult[String, V] = {
        case scala.util.Success(x) => Success(x)
        case scala.util.Failure(failure) => ExecutionError(failure.getMessage)
    }
}
