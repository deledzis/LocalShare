package com.deledzis.localshare.common.usecase

sealed class Error(val exception: Exception?) {
    class NetworkError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class NetworkConnectionError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class GenericError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class ResponseError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class PersistenceError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }

    class UnsupportedOperationError(exception: Exception? = null) : Error(exception = exception) {
        override fun equals(other: Any?): Boolean {
            return this === other
        }

        override fun hashCode(): Int {
            return System.identityHashCode(this)
        }
    }
}