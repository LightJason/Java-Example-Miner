!main.

+!main <-
    .generic/print("miner starts running");
    //.walk/goal(0, 0);
    !run.

-!main <-
    .generic/print("main error").

+!run <-
    .generic/print("run").
    .walk/forward;
    !run.

-!run <-
    .generic/print("run error").
