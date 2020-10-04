!main.

+!main <-
    .generic/print("miner starts running");
    !step
.

+!step <-
    .move/forward;
    !step
.

-!step <-
    !step/right
.

+!step/right <-
    .move/right;
    !step
.

-!step/right <-
    !step/left
.

+!step/left <-
    .move/left;
    !step
.

-!step/left <-
    !step/back
.

+!step/back <-
    .move/backward;
    !step
.

-!step/back <-
    !step
.

+!goal/position(P) <-
    .generic/print("goal", P);
    .goal/next
.

+!perceive/gem(position(P), type(T), value(V)) <-
    //.generic/print("perceive gem", T, V);
    .goal/add(P)
.
