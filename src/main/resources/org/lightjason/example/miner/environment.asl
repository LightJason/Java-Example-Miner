!main.

+!main <-
    .world/create(50, 50);
    .world/start( 1000, 1000 );
    .generic/print("environment start")
    .

+!world/start <-
    .generic/print("world start");
    .solid/create(15, 15, 20, 20);
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