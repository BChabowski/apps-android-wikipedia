package org.wikipedia.testutils

enum class DefaultTestTimeouts(val value: Long) {
    DEFAULT_TIMEOUT_IN_MILLISECONDS(2000L),
    DEFAULT_POLL_INTERVAL(250L);
}