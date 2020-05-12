package com.github.fj.android.anim

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator

fun Animator?.startSafely() {
    if (this == null) {
        return
    }

    if (isRunning) {
        return
    }

    start()
}

fun Animator?.cancelSafely() {
    if (this == null) {
        return
    }

    if (isRunning) {
        cancel()
    }
}

/**
 * Resets all animation status of given [Animator]. However, only [ValueAnimator] states could be
 * rewound if given [Animator] instance is [AnimatorSet].
 *
 * @return `true` if all animation state of given [Animator] is/are rewound, `false` otherwise.
 */
fun Animator.resetAll(): Boolean = if (this is AnimatorSet) {
    var flags = true
    childAnimations.forEach {
        flags = flags or it.resetAllInternal()
    }

    /* return if */ flags
} else {
    /* return if */ if (this is ValueAnimator) {
        resetAllInternal()
    } else {
        false
    }
}

private fun Animator.resetAllInternal(): Boolean {
    if (this !is ValueAnimator) {
        return false
    }

    this.duration = 0
    this.reverse()
    this.end()

    return true
}
