!main.

+!main <-
    .generic/print("miner starts running");
    !step/forward
.

+!step/forward <-
    .generic/print("forward");
    .move/forward;
    !step/forward
.

-!step/forward <-
    .generic/print("forward fail");
    !step/right
.

+!step/right <-
    .generic/print("right");
    .move/right;
    !step/forward
.

-!step/right <-
    .generic/print("right fail");
    !step/left
.

+!step/left <-
    .generic/print("left");
    .move/left;
    !step/forward
.

-!step/left <-
    .generic/print("left fail");
    !step/back
.

+!step/back <-
    .generic/print("back");
    .move/backward;
    !step/forward
.

-!step/back <-
    .generic/print("back fail");
    !step/forward
.
