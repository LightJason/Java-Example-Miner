!main.

+!main <-
    .world/create(50, 50);
    .world/start( 1000, 1000 );
    .generic/print("environment start")
    .

+!world/start <-
    .generic/print("world start");
    .solid/create(0, 15, 30, 20);
    .gem/ruby/create( 39, 39 );
    .gem/amethyst/create( 39, 44);
    .gem/sapphirine/create( 39, 34 );
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