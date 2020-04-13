!main.

+!main <-
    .world/create(50, 50);
    .world/start( 1200, 1000 );
    .generic/print("environment start").

+!world/start <-
    .generic/print("world start")
    .

/*
+!world/empty <-
    .generic/print("dead").

+!world/iteration(N) <-
    .generic/print("iteration", N).
*/