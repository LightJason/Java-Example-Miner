!main.

+!main <-
    .world/create(50, 50);
    .world/start( 1000, 1000 );
    .generic/print("environment start")
    .

+!world/start <-
    .generic/print("world start");
    .solid/create(0, 15, 30, 20);
    .gem/ruby/create( 40, 40 );
    .gem/amethyst/create( 40, 45 );
    .gem/sapphirine/create( 40, 35 );
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