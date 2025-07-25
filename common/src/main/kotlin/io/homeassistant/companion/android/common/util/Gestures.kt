package io.homeassistant.companion.android.common.util

import androidx.annotation.StringRes
import io.homeassistant.companion.android.common.R
import kotlin.math.abs

enum class GestureAction(@StringRes val description: Int) {
    NONE(R.string.none),
    QUICKBAR_DEFAULT(R.string.gestures_action_quickbar_default),
    SERVER_LIST(R.string.gestures_action_server_list),
    SERVER_NEXT(R.string.gestures_action_server_next),
    SERVER_PREVIOUS(R.string.gestures_action_server_previous),
}

enum class GestureDirection(@StringRes val description: Int) {
    /** A gesture from bottom to top */
    UP(R.string.gestures_direction_up),

    /** A gesture from top to bottom */
    DOWN(R.string.gestures_direction_down),

    /** A gesture from right to left */
    LEFT(R.string.gestures_direction_left),

    /** A gesture from left to right */
    RIGHT(R.string.gestures_direction_right),
    ;

    companion object {
        fun fromVelocity(velocityX: Float, velocityY: Float): GestureDirection {
            return if (abs(velocityX) > abs(velocityY)) {
                if (velocityX > 0) {
                    RIGHT
                } else {
                    LEFT
                }
            } else {
                if (velocityY > 0) {
                    DOWN
                } else {
                    UP
                }
            }
        }
    }
}

/** Number of pointers used in a gesture */
enum class GesturePointers(
    val asInt: Int,
    @StringRes val description: Int,
) {
    TWO(asInt = 2, description = R.string.gestures_pointers_two),
    THREE(asInt = 3, description = R.string.gestures_pointers_three),
}

/**
 * Enum with all supported gestures by the app. Currently, a gesture
 * is always a combination of swipe + a direction and pointer count.
 */
enum class HAGesture(
    val direction: GestureDirection,
    val pointers: GesturePointers,
) {
    SWIPE_LEFT_TWO(GestureDirection.LEFT, GesturePointers.TWO),
    SWIPE_LEFT_THREE(GestureDirection.LEFT, GesturePointers.THREE),
    SWIPE_RIGHT_TWO(GestureDirection.RIGHT, GesturePointers.TWO),
    SWIPE_RIGHT_THREE(GestureDirection.RIGHT, GesturePointers.THREE),
    SWIPE_UP_TWO(GestureDirection.UP, GesturePointers.TWO),
    SWIPE_UP_THREE(GestureDirection.UP, GesturePointers.THREE),
    SWIPE_DOWN_TWO(GestureDirection.DOWN, GesturePointers.TWO),
    SWIPE_DOWN_THREE(GestureDirection.DOWN, GesturePointers.THREE),
    ;

    companion object {
        /** @return [HAGesture] for a detected swipe if supported, or `null` otherwise */
        fun fromSwipeListener(direction: GestureDirection, pointers: Int): HAGesture? =
            entries.firstOrNull { it.direction == direction && it.pointers.asInt == pointers }
    }
}
