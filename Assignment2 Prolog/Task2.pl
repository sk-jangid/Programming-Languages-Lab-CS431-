:-dynamic
    % A reversed path according to Time, Dist or Cost
    reversePath/5.

bus(510, 'Amingaon', 'Maligaon', 14.5, 15, 10, 10).
bus(201, 'Amingaon', 'Chandmari', 16, 16.5, 7, 15).
bus(123, 'Amingaon', 'Jalukbari', 14, 14.5, 10, 10).
bus(314, 'Jalukbari', 'Panbazar', 16, 17, 7, 8).
bus(756, 'Panbazar', 'Chandmari', 12, 12.5, 7, 8).
bus(145, 'Panbazar', 'Paltanbazar', 13, 13.5, 7, 8).
bus(105, 'Chandmari', 'Maligaon', 17, 17.5, 7, 8).
bus(415, 'Maligaon', 'Lokhra', 14.5, 15, 10, 9).

timeEdge(Bus,Source, Dest, Time, Distance, Cost):- bus(Bus,Source, Dest,Departure_time, Arrival_time,Distance,Cost), Time is Arrival_time - Departure_time.
distEdge(Bus,Source, Dest, Time, Distance, Cost):- bus(Bus,Source, Dest, Departure_time,Arrival_time,Distance, Cost), Time is Arrival_time - Departure_time.
costEdge(Bus,Source, Dest, Time, Distance, Cost):- bus(Bus,Source, Dest, Departure_time,Arrival_time,Distance, Cost), Time is Arrival_time - Departure_time.


distPath(Bus,Source, Dest, Time, Distance, Cost):- distEdge(Bus,Source, Dest, Time, Distance, Cost).
costPath(Bus,Source, Dest, Time, Distance, Cost):- costEdge(Bus,Source, Dest, Time, Distance, Cost).
timePath(Bus,Source, Dest, Time, Distance, Cost):- timeEdge(Bus,Source, Dest, Time, Distance, Cost).

isMember(Temp, [Temp|RestPath]):- RestPath is RestPath.
isMember(Temp, [Temp1|RestPath]):-isMember(Temp, RestPath), Temp1 is Temp1.

notInPath(Temp, Path):-isMember(Temp, Path),
 !, fail.
notInPath(_,_).


checkShortPathTime([Source|RestPath], Time, Dist, Cost):-
    reversePath([Source|Path1], Time1,_,_, 'time'), !, Time<Time1,Path1 is Path1,
    retract(reversePath([Source|_],_,_,_,'time')),
    assert(reversePath([Source|RestPath], Time, Dist, Cost, 'time')).
checkShortPathTime(Path, Time, Dist, Cost):-
    assert(reversePath(Path, Time,Dist,Cost,'time')).

checkShortPathDist([Source|RestPath],Time, Dist, Cost):-
    reversePath([Source|Path1], _,Dist1,_, 'dist'), !, Dist<Dist1, Path1 is Path1,
    retract(reversePath([Source|_],_,_,_,'dist')),
    assert(reversePath([Source|RestPath], Time, Dist, Cost, 'dist')).
checkShortPathDist(Path, Time, Dist, Cost):-
    assert(reversePath(Path, Time, Dist, Cost, 'dist')).

checkShortPathCost([Source|RestPath],Time, Dist, Cost):-
    reversePath([Source|Path1], _, _, Cost1, 'cost'), !, Cost<Cost1,Path1 is Path1,
    retract(reversePath([Source|_],_,_,_,'cost')),
    assert(reversePath([Source|RestPath], Time, Dist, Cost, 'cost')).
checkShortPathCost(Path, Time, Dist, Cost):-
    assert(reversePath(Path, Time,Dist,Cost, 'cost')).


traverseTimePath(Source, Path, Time, Dist, Cost):-
    timePath(Bus,Source,Temp, Time1, Dist1, Cost1),
    notInPath(Temp,Path),
    TimeNew is Time+Time1,
    DistNew is Dist+Dist1,
    CostNew is Cost+Cost1,
    checkShortPathTime([Temp|[Bus|[Source|Path]]], TimeNew, DistNew, CostNew),
    traverseTimePath(Temp,[Bus|[Source|Path]], TimeNew, DistNew, CostNew).

traverseTimePath(Source):-
    retractall(reversePath(_,_,_,_,'time')),
    traverseTimePath(Source,[],0,0,0).
traverseTimePath(_).

traverseCostPath(Source, Path, Time, Dist, Cost):-
    costPath(Bus,Source,Temp, Time1, Dist1, Cost1),
    notInPath(Temp,Path),
    TimeNew is Time+Time1,
    DistNew is Dist+Dist1,
    CostNew is Cost+Cost1,
    checkShortPathCost([Temp|[Bus|[Source|Path]]], TimeNew, DistNew, CostNew),
    traverseCostPath(Temp,[Bus|[Source|Path]], TimeNew, DistNew, CostNew).

traverseCostPath(Source):-
    retractall(reversePath(_,_,_,_,'cost')),
    traverseCostPath(Source,[],0,0,0).
traverseCostPath(_).

traverseDistPath(Source, Path, Time,Dist, Cost):-
    distPath(Bus,Source,Temp,Time1,Dist1,Cost1),
    notInPath(Temp, Path),
    TimeNew is Time +Time1,
    DistNew is Dist+Dist1,
    CostNew is Cost+Cost1,
    checkShortPathDist([Temp|[Bus|[Source|Path]]],TimeNew, DistNew, CostNew),
    traverseDistPath(Temp,[Bus|[Source|Path]], TimeNew, DistNew, CostNew).

traverseDistPath(Source):-
    retractall(reversePath(_,_,_,_,'dist')),
    traverseDistPath(Source,[],0,0,0).

traverseDistPath(_).


optimalTimePath(Source, Dest):-
   traverseTimePath(Source),
   reversePath([Dest|Path], Time, Dist, Cost, 'time')
   ->(
       reverse([Dest|Path], Path1),
       writef('Optimum Time path is\n%w\nDistance:%w Time:%w Cost:%w\n',[Path1, Dist, Time, Cost])
   )
   ;writef('There is no Path from %w to %w\n', [Source, Dest]).

optimalDistancePath(Source, Dest):-
   traverseDistPath(Source),
   reversePath([Dest|Path], Time, Dist, Cost, 'dist')
   ->(
       reverse([Dest|Path], Path1),
       writef('Optimum distance path \n%w\nDistance: %w, Time:%w, Cost:%w\n',[Path1, Dist, Time, Cost])
   )
   ;writef('There is no Path from %w to %w\n', [Source, Dest]).


optimalCostPath(Source, Dest):-
   traverseCostPath(Source),
   reversePath([Dest|Path], Time,Dist,Cost, 'cost')
   ->(
       reverse([Dest|Path], Path1),
       writef('Optimum cost path\n%w\n Distance:%w, Time:%w, Cost:%w\n',[Path1, Dist, Time, Cost])
   )
   ;writef('There is no Path from %w to %w\n', [Source, Dest]).


route(Source, Dest):-
optimalTimePath(Source, Dest),
optimalCostPath(Source, Dest),
optimalDistancePath(Source, Dest).















