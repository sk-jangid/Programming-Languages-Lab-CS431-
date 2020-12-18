distance('G1','G5',4).
distance('G2','G5',6).
distance('G3','G5',8).
distance('G4','G5',9).
distance('G1','G6',10).
distance('G2','G6',9).
distance('G3','G6',3).
distance('G4','G6',5).
distance('G5','G7',3).
distance('G5','G10',4).
distance('G5','G11',6).
distance('G5','G12',7).
distance('G5','G6',7).
distance('G5','G8',9).
distance('G6','G8',2).
distance('G6','G12',3).
distance('G6','G11',5).
distance('G6','G10',9).
distance('G6','G7',10).
distance('G7','G10',2).
distance('G7','G11',5).
distance('G7','G12',7).
distance('G7','G8',10).
distance('G8','G9',3).
distance('G8','G12',3).
distance('G8','G11',4).
distance('G8','G10',8).
distance('G10','G15',5).
distance('G10','G11',2).
distance('G10','G12',5).
distance('G11','G15',4).
distance('G11','G13',5).
distance('G11','G12',4).
distance('G12','G13',7).
distance('G12','G14',8).
distance('G15','G13',3).
distance('G13','G14',4).
distance('G14','G17',5).
distance('G14','G18',4).
distance('G17','G18',8).


distance_edges(Gate1, Gate2, Dist):- distance(Gate1, Gate2 ,Dist).

first('G1').
first('G2').
first('G3').
first('G4').
last('G17').



is_last(Head):- last(Head).
%is_last(_).
paths(Source,Path):- last(Source),reverse([Source|Path],Path1),writef("%w\n",[Path1]).
paths(Source,Path):- not(is_last(Source)),distance_edges(Source,X,Dist),not(memberchk(X, Path)),paths(X,[Source|Path]),Dist is Dist,fail.

%show all the paths
paths():-first(X),paths(X,[]).









