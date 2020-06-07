!main.

+!main <-
    .generic/print("miner starts running");
    !step/forward
.

+!step/forward <-
    .move/forward;
    !step/forward
.

-!step/froward <-
    !step/right
.

+!step/right <-
    .move/right
.

-!step/right <-
    !step/left
.

+!step/left <-
    .move/left
.

-!step/left <-
    !step/back
.

+!step/back <-
    .move/backward
.

-!step/back <-
    !step/forward
.
