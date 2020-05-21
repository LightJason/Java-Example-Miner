!main.

+!main <-
    .world/create(250, 250);
    .world/start( 1000, 1000 );
    .generic/print("environment start")
    .

+!world/start <-
    .generic/print("world start");
    .miner/create
    .

/*
+!world/empty <-
    .generic/print("world empty")
    .

+!world/iteration(N) <-
    .generic/print("world iteration", N)
    .
*/